import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class ParallelTestNG {

    // Using ThreadLocal to ensure thread safety for WebDriver instances
    ThreadLocal<WebDriver> driverThreadLocal= new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        // initialize the WebDriver locally for each thread to avoid conflicts due to parallel execution
        // Using ThreadGuard to protect the WebDriver instance from being used in multiple threads
        WebDriver LocalDriver = ThreadGuard.protect(new ChromeDriver(options));

       // Setting the WebDriver instance to ThreadLocal
        driverThreadLocal.set(LocalDriver);

        driverThreadLocal.get().manage().window().maximize(); //get the driver instance from ThreadLocal
        driverThreadLocal.get().get("https://the-internet.herokuapp.com/login");
    }

    @Test
    public void ValidLoginTC(){
        driverThreadLocal.get().findElement(By.id("username")).sendKeys("tomsmith");
        driverThreadLocal.get().findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driverThreadLocal.get().findElement(By.className("radius")).click();

        WebDriverWait wait = new WebDriverWait(driverThreadLocal.get(), Duration.ofSeconds(10));
        String ActualLoginMsg = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("flash"))).getText();

        Assert.assertTrue(ActualLoginMsg.contains("You logged into a secure area!"), "Login message is not displayed");
    }

    @Test
    public void InValidUsernameLoginTC(){
        driverThreadLocal.get().findElement(By.id("username")).sendKeys("wrongUsername");
        driverThreadLocal.get().findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driverThreadLocal.get().findElement(By.className("radius")).click();

        WebDriverWait wait = new WebDriverWait(driverThreadLocal.get(), Duration.ofSeconds(10));
        String ActualMsg = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("flash"))).getText();

        Assert.assertTrue(ActualMsg.contains("Your username is invalid!"), "Login message is not displayed");
    }

    @Test
    public void InValidPasswordLoginTC(){
        driverThreadLocal.get().findElement(By.id("username")).sendKeys("tomsmith");
        driverThreadLocal.get().findElement(By.id("password")).sendKeys("wrongPassword!");
        driverThreadLocal.get().findElement(By.className("radius")).click();

        WebDriverWait wait = new WebDriverWait(driverThreadLocal.get(), Duration.ofSeconds(10));
        String ActualMsg = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("flash"))).getText();

        Assert.assertTrue(ActualMsg.contains("Your password is invalid!"), "Login message is not displayed");
    }

    @AfterMethod (alwaysRun = true)
    public void teardown(){
        driverThreadLocal.get().quit();
    }

}
