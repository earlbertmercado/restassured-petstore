package com.earlbertmercado.restassured.petstore.base;

import com.earlbertmercado.restassured.petstore.clients.PetClient;
import com.earlbertmercado.restassured.petstore.clients.StoreClient;
import com.earlbertmercado.restassured.petstore.clients.UserClient;
import com.earlbertmercado.restassured.petstore.config.ConfigManager;
import com.earlbertmercado.restassured.petstore.utils.ReportManager;
import io.restassured.RestAssured;
import org.testng.ITestResult;
import org.testng.annotations.*;

public abstract class BaseTest {

    protected PetClient petClient;
    protected StoreClient storeClient;
    protected UserClient userClient;

    @BeforeClass
    public void setupClients() {
        this.petClient = new PetClient();
        this.storeClient = new StoreClient();
        this.userClient = new UserClient();
    }

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
