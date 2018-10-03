package com.dynamite.shopping.testlisteners;

import com.dynamite.shopping.exceptions.HtmlElementsException;
import com.dynamite.shopping.utils.Driver.BrowserDriver;
import com.dynamite.shopping.utils.Driver.Screenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Field;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            Screenshot screenshot = new Screenshot(getDriverFromBaseTest(result));
            screenshot.capture(result.getName());
        } catch (Exception ex) {
            throw new HtmlElementsException(ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private WebDriver getDriverFromBaseTest(ITestResult result) throws IllegalAccessException {
        WebDriver driver = null;

        try {
            Class<? extends ITestResult> testClass = (Class<? extends ITestResult>)
                    result.getInstance().getClass();

            Class<? extends ITestResult> baseTestClass = (Class<? extends ITestResult>)
                    testClass.getSuperclass();

            Field driverField = baseTestClass.getDeclaredField("driver");
            driver = (BrowserDriver) driverField.get(result.getInstance());
            return driver;
        } catch (SecurityException | NoSuchFieldException | IllegalArgumentException ex) {
            throw new HtmlElementsException("error getting the driver from base test");
        }

    }

    @Override
    public void onTestSuccess(ITestResult result) {
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }

    @Override
    public void onTestStart(ITestResult arg0) {
    }

}
