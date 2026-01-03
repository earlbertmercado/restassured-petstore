package com.earlbertmercado.restassured.petstore.base;

import com.earlbertmercado.restassured.petstore.config.ConfigManager;
import com.earlbertmercado.restassured.petstore.utils.ReportManager;
import io.restassured.RestAssured;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public abstract class BaseTest {

    @BeforeSuite
    public void beforeSuite() {
        RestAssured.baseURI = ConfigManager.getBaseUri();
    }

    @AfterSuite
    public void afterSuite() {
        ReportManager.logInfo("Test Suite Completed");
        ReportManager.flush();
    }

    @BeforeMethod
    public void beforeMethod(ITestResult result) {
        String className = result
                .getMethod()
                .getTestClass()
                .getRealClass()
                .getSimpleName();
        String testName =  result.getMethod().getMethodName();
        ReportManager.startTest(testName, className);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                ReportManager.logPass("Test passed: " + testName);
                break;
            case ITestResult.FAILURE:
                ReportManager.logFail("Test failed: " + testName);
                ReportManager.logFail(result.getThrowable().getMessage());
                break;
            case ITestResult.SKIP:
                ReportManager.logWarning("Test skipped: " + testName);
                break;
        }

        ReportManager.logInfo("Completed test: " + testName);
    }
}
