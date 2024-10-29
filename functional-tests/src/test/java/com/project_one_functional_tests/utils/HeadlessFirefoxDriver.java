package com.project_one_functional_tests.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class HeadlessFirefoxDriver {

    private WebDriver driver;

    public HeadlessFirefoxDriver() {
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
        String headlessEnv = System.getProperty("headless", "false");
        boolean headless = Boolean.parseBoolean(headlessEnv);
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }
        driver = new FirefoxDriver(options);
    }

    public WebDriver getDriver() {
        return this.driver;
    }
}
