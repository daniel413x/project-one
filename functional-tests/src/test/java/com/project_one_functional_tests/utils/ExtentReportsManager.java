package com.project_one_functional_tests.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;

public class ExtentReportsManager {

    private static ExtentReports extentReports;

    private ExtentReportsManager() {
    }

    public static ExtentReports getInstance() {
        if (extentReports == null) {
            String reportPath = "target/extent-report/ExtentReport.html";

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            // configure which views to include
            sparkReporter.viewConfigurer()
                .viewOrder()
                .as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST, ViewName.CATEGORY })
                .apply();

            sparkReporter.config().setTimelineEnabled(true);
            sparkReporter.config().setOfflineMode(true);
            sparkReporter.config().setReportName("Axe-core Accessibility Report");
            sparkReporter.config().setDocumentTitle("Accessibility Test Results");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
        }
        return extentReports;
    }
}
