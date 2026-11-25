import RetryAnalyzer.Retry;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Retry_Analyzer {
    WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/login");
    }

    @Test(retryAnalyzer = Retry.class)
    public void ValidLoginTC(){
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.className("radius")).click();
        String ActualLoginMsg = driver.findElement(By.id("flash")).getText();
        Assert.assertTrue(ActualLoginMsg.contains("You logged into a secure area!"), "Login message is not displayed");
        throw new RuntimeException(); // This will cause the test to fail
    }

    @AfterMethod (alwaysRun = true)
    public void teardown(){
        driver.quit();
    }

}
