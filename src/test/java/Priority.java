import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Priority {
    WebDriver driver;

    @BeforeClass
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/login");
    }

    /* Note : Logic of using priority here is wrong, because the test cases are dependent on each other.
     If the login test case fails, the logout test case will also fail.
     So, we should use dependsOnMethods instead of priority.*/
    // This is just for demonstration purpose to show how priority works in TestNG.

    @Test (priority = 1)
    public void LoginTest(){
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.className("radius")).click();


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String ActualLoginMsg = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("flash"))).getText();

        Assert.assertTrue(ActualLoginMsg.contains("You logged into a secure area!"), "Login message is not displayed");

    }

    @Test (priority = 2)
    public void LogoutTest(){
        driver.findElement(By.cssSelector(".example>a")).click();
        String ActualLogoutMsg =  driver.findElement(By.id("flash")).getText();
        Assert.assertTrue(ActualLogoutMsg.contains("You logged out of the secure area!"), "Logout message is not displayed");

    }


    @AfterClass
    public void teardown(){
        driver.quit();
    }

}
