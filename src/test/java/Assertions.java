import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Assertions {

    WebDriver driver;

    @BeforeMethod
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

    }

    @Test //Hard assertion
    public void InvalidLoginTC(){
        /* Steps to perform invalid login test case:
        1. Enter invalid username (if you want to test invalid username, you can enter a valid password)
        2. Enter invalid password (if you want to test invalid password, you can enter a valid username)
        3. Click on the login button
        4. Verify (Assert) that the error message is displayed
        */
        driver.findElement(By.id("user-name")).sendKeys("Shehab");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String ActualErrorMsg = driver.findElement(By.cssSelector("h3")).getText();
        //Hard assertion: if the first assertion fails, the test will stop executing and the second assertion will not be executed
        Assert.assertEquals( ActualErrorMsg, "Epic sadface: Username and password do not match any user in this service" , "Error message is not displayed");
        Assert.assertFalse(driver.getCurrentUrl().contains("/inventory.html") , "User is logged in with invalid credentials");
    }

    @Test //Soft assertion
    public void InvalidLoginTCSoft(){
        SoftAssert softAssert = new SoftAssert();

        driver.findElement(By.id("user-name")).sendKeys("Shehab");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String ActualErrorMsg = driver.findElement(By.cssSelector("h3")).getText();
        //Soft assertion: if the first assertion fails, the test will continue executing and the second assertion will be executed
        softAssert.assertEquals( ActualErrorMsg, "Epic sadface: Username and password do not match any user in this service" , "Error message is not displayed");
        softAssert.assertFalse(driver.getCurrentUrl().contains("/inventory.html") , "User is logged in with invalid credentials");
        softAssert.assertAll(); //This will report all the assertions that failed in the test case (mandatory when using soft assertions)
    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }

}
