import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

public class XMLGroups {
    WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/login");
    }
    @Test(groups = "loginFun")
    public void ValidLoginTC(){
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.className("radius")).click();
        String ActualLoginMsg = driver.findElement(By.id("flash")).getText();
        Assert.assertTrue(ActualLoginMsg.contains("You logged into a secure area!"), "Login message is not displayed");

    }
    @Test(groups = "loginFun")
    public void InValidLoginTC(){
        driver.findElement(By.id("username")).sendKeys("shehab");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.className("radius")).click();
        String ActualMsg = driver.findElement(By.id("flash")).getText();
        Assert.assertTrue(ActualMsg.contains("Your username is invalid!"), "Login message is not displayed");

    }

    @AfterMethod (alwaysRun = true)
    public void teardown(){
        driver.quit();
    }

}
