package com.project_one_functional_tests.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportsManager {

    private static ExtentReports extentReports;

    private ExtentReportsManager() {
    }

    public static ExtentReports getInstance() {
        if (extentReports == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("ExtentReport.html");
            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
        }
        return extentReports;
    }
}
