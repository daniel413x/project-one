package com.project_one_functional_tests.steps;


import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.project_one_functional_tests.utils.HeadlessChromeDriver;
import com.project_one_functional_tests.utils.ResetDatabase;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class BaseSteps {

    WebDriver driver;

    @BeforeAll
    public static void resetDatabaseBeforeAll() {
        ResetDatabase.run();
    }

    @Before
    public void setUp() {
        driver = HeadlessChromeDriver.getDriver();
    }

    @After
    public void tearDown() {
        HeadlessChromeDriver.quitDriver();
    }

    public void pressKeyNTimes(Keys key, int n) {
        Actions actions = new Actions(driver);
        for (int t = 0; t < n; t += 1) {
            actions.sendKeys(key).pause(Duration.ofSeconds(1)).perform();
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
}
