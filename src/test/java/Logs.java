import ListenersINTestNG.TestNGListeners;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Logs {
    WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setup(){

        LogUtils.info("Setup method is running");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/login");

        LogUtils.info("page navigated to login page");
    }

    @Test()
    public void ValidLoginTC(){
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        LogUtils.info("UserName is Entered");

        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        LogUtils.info("Password is Entered");

        driver.findElement(By.className("radius")).click();
        LogUtils.info("Login Button is Clicked");

        String ActualLoginMsg = driver.findElement(By.id("flash")).getText();
        Assert.assertTrue(ActualLoginMsg.contains("You logged into a secure area!"), "Login message is not displayed");

    }

    @AfterMethod (alwaysRun = true)
    public void teardown(){
        driver.quit();
    }

}
