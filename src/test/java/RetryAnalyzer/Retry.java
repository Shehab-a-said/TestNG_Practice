package RetryAnalyzer;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

// IRetryAnalyzer allows you to control the retry behavior of test methods.

public class Retry implements IRetryAnalyzer {

    private int attempts = 0;
    @Override
    public boolean retry(ITestResult iTestResult) {
        if (iTestResult.getStatus() == ITestResult.FAILURE && attempts==0)
        {
            attempts++;
            return true ; // Retry the test

        }
        return false; // Do not retry the test

    }
}
