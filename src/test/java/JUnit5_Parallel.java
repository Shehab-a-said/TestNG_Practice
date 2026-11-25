import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

// This test class demonstrates parallel execution of tests using JUnit 5.
// properties file of JUnit5 should be configured to enable parallel execution of tests.
// this file exists in the root of the project src/main/resources/ with name "junit-platform.properties"

public class JUnit5_Parallel {

    WebDriver driver;

    @BeforeEach
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito" , "--headless");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/login");
    }

    @RepeatedTest(5) // This test will run 5 times in parallel
    public void ValidLoginTC(){
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.className("radius")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String ActualLoginMsg = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("flash"))).getText();

        Assertions.assertTrue(ActualLoginMsg.contains("You logged into a secure area!"), "Login message is not displayed");
    }

    @AfterEach
    public void teardown(){
        driver.quit();
    }

}
