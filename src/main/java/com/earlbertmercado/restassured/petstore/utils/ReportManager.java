package com.earlbertmercado.restassured.petstore.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.earlbertmercado.restassured.petstore.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ReportManager {

    private static final Logger logger = LogManager.getLogger(ReportManager.class);
    private static ExtentReports extent;
    private static final Map<Long, ExtentTest> extentTestMap = new HashMap<>();
    private static final String REPORT_PATH = ConfigManager.getReportPath();

    static {
        createReportDirectory();
        initializeExtentReports();
    }

    private static void createReportDirectory() {
        File directory = new File(REPORT_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private static void initializeExtentReports() {
        String reportName = "PetStore_Test_Report.html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_PATH + reportName);
        sparkReporter.config().setDocumentTitle("PetStore API Test Report");
        sparkReporter.config().setReportName("Automation Test Results");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setEncoding("utf-8");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", System.getProperty("env", "TEST"));
    }

    public static synchronized void startTest(String testName, String className) {
        ExtentTest test = extent.createTest(testName).assignCategory(className);
        extentTestMap.put(Thread.currentThread().threadId(), test);
        logger.info("Test started: {}", testName);
    }

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get(Thread.currentThread().threadId());
    }

    public static synchronized void logInfo(String message) {
        getTest().log(Status.INFO, message);
        logger.info(message);
    }

    public static synchronized void logPass(String message) {
        getTest().log(Status.PASS, message);
        logger.info("PASS: {}", message);
    }

    public static synchronized void logFail(String message) {
        getTest().log(Status.FAIL, message);
        logger.error("FAIL: {}", message);
    }

    public static synchronized void logWarning(String message) {
        getTest().log(Status.WARNING, message);
        logger.warn("WARNING: {}", message);
    }

    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
            logger.info("Extent report flushed");
        }
    }
}
