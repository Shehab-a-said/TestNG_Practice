import ListenersINTestNG.TestNGListeners;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

@Listeners(TestNGListeners.class)
// defined here or in testng.xml file. (Parameter_Plus_Listeners.xml)
// If defined in both places, the one defined in the testng.xml file will take precedence over the one defined in the class.
// This annotation is used to specify the listener class that will be used for this test class.

public class XMLParameter_Plus_Listeners {
    WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/login");
    }

    /* public void ValidLoginTC(@Optional("tomsmith") String username,@Optional("SuperSecretPassword!") String password)
    @Optional annotation is used to provide default values for the parameters in case they are not specified in the testng.xml file.
    This allows the test to run from class without requiring the parameters to be passed in from the XML file.
    without @Optional annotation, if the parameters are not specified in the testng.xml file, the test will fail */

    @Parameters({"username", "password"})
    @Test
    public void ValidLoginTC(String username,String password){
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("radius")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String ActualLoginMsg = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("flash"))).getText();

        Assert.assertTrue(ActualLoginMsg.contains("You logged into a secure area!"), "Login message is not displayed");

    }

    @AfterMethod(alwaysRun = true)
    public void teardown(){
        driver.quit();
    }
}
