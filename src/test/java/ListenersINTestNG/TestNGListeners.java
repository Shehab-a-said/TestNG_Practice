package ListenersINTestNG;

import org.testng.*;

// InvokedMethodListener allows you to listen to the invocation of test methods and configuration methods.
// ITestListener allows you to listen to the invocation of test methods only and their results.
// IExecutionListener allows you to listen to the start and finish of the entire test execution.


public class TestNGListeners implements IInvokedMethodListener , ITestListener , IExecutionListener {

    //By default, invoked before any method annotated with @Test and @BeforeMethod
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

        // This condition is to be invoked only for test methods, not for configuration methods
        if (method.isTestMethod())
        {
            System.out.println(method.getTestMethod().getMethodName() + " Started ");
        }
    }

    // By default, invoked after any method annotated with @Test and @AfterMethod
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

        // This condition is to be invoked only for test methods, not for configuration methods
        if (method.isTestMethod())
        {
            System.out.println(method.getTestMethod().getMethodName() + " Finished ");
        }
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " is Passed");
    }

    public void onTestFailure(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " is Failed");
    }

    public void onTestSkipped(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " is Skipped");
    }


    public void onExecutionStart() {
        System.out.println("TestNG Execution Started");
    }

    public void onExecutionFinish() {
        System.out.println("TestNG Execution Finished");
    }


}
