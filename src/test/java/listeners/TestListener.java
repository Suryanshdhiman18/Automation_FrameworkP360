package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.*;
import com.aventstack.extentreports.*;
import utils.*;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTestManager.startTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().pass("Test Passed");
    }
    
    @Override
    public void onTestFailure(ITestResult result) {

        ExtentTestManager.getTest().fail(result.getThrowable());

        Object testClass = result.getInstance();
        WebDriver driver = ((base.BaseTest) testClass).getDriver();

        // 👇 NOW YOU GET PATH
        String path = ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());

        try {
            ExtentTestManager.getTest().addScreenCaptureFromPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
    }
}