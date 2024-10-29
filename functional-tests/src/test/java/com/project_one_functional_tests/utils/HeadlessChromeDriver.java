package com.project_one_functional_tests.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class HeadlessChromeDriver {
    public static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            String headlessEnv = System.getProperty("headless", "false");
            boolean headless = Boolean.parseBoolean(headlessEnv);
            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.addArguments("--headless");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--remote-debugging-pipe");
            }
            driver = new ChromeDriver(options);
        }
        return driver;
    }
    
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}