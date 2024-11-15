package com.project_one_functional_tests.steps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.project_one_functional_tests.utils.ExtentReportsManager;
import com.project_one_functional_tests.utils.HeadlessChromeDriver;
import com.project_one_functional_tests.utils.ResetDatabase;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BaseSteps {

    // Driver
    WebDriver driver = HeadlessChromeDriver.getDriver();
    public Actions actions = new Actions(driver);

    // ExtentReports setup
    public static ExtentReports extentReports = ExtentReportsManager.getInstance();
    public ExtentTest extentTest;

    // axe-core
    public AxeBuilder axeBuilder = new AxeBuilder();
    public Results axeResults;

    @Before
    public void before() {
        ResetDatabase.run();
        driver = HeadlessChromeDriver.getDriver();
    }

    @After
    public void after() {
        HeadlessChromeDriver.quitDriver();
    }

    @AfterAll
    public static void afterAll() {
        extentReports.flush();
    }

    public void pressKeyNTimes(Keys key, int n) {
        Actions actions = new Actions(driver);
        for (int t = 0; t < n; t += 1) {
            actions.sendKeys(key).pause(Duration.ofMillis(200)).perform();
        }
    }

    @Given("I press the arrow key down {int} times")
    public void iPressTheArrowKeyDnIntTimes(int n) {
        pressKeyNTimes(Keys.ARROW_DOWN, n);
    }

    @When("I press the arrow key up {int} times")
    public void iPressTheArrowKeyUpIntTimes(int n) {
        pressKeyNTimes(Keys.ARROW_UP, n);
    }

    @When("I press the tab key {int} times")
    public void iPressTheTabKeyStringTimes(int n) {
        pressKeyNTimes(Keys.TAB, n);
    }

    @When("I press the tab key once")
    public void iPressTheTabKeyOnce() {
        pressKeyNTimes(Keys.TAB, 1);
    }

    @When("I assess the axe-core violations labeled {string} in Extent reports")
    public void iAssessAxeResults(String string) {
        extentTest = extentReports.createTest(string);
        axeResults = axeBuilder.analyze(driver);
    }

    @Then("there should be no violations")
    public void thereShouldBeNoViolations() {
        extentTest.info("Checking accessibility violations count");
        boolean noViolations = axeResults.violationFree();

        try {
            assertTrue(noViolations, "Accessibility violations detected, test failed.");
            extentTest.pass("No accessibility violations found");
        } catch (AssertionError e) {
            extentTest.fail("Accessibility violations detected: " + axeResults.getViolations().size());

            axeResults.getViolations().forEach(violation -> {
                extentTest.info("Violation: " + violation.getDescription());
                extentTest.info("Help URL: " + violation.getHelpUrl());
                violation.getNodes().forEach(node -> {
                    extentTest.info("Element affected: " + node.getHtml());
                    extentTest.info("Failure Summary: " + node.getFailureSummary());
                });
            });
            // Fail the test
            throw e;
        }
    }
}
