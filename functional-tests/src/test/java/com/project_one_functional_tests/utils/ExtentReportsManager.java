package com.project_one_functional_tests.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

// currently only used for axe-core
public class ExtentReportsManager {

    private static ExtentReports extentReports;

    private ExtentReportsManager() {
    }

    public static ExtentReports getInstance() {
        if (extentReports == null) {
            String reportPath = "target/extent-report/axe-core-accessibility-report.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
        }
        return extentReports;
    }
}
