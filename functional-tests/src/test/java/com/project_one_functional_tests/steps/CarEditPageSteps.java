package com.project_one_functional_tests.steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.project_one_functional_tests.pages.CarEditPage;
import com.project_one_functional_tests.utils.HeadlessChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class CarEditPageSteps {

    WebDriver driver = HeadlessChromeDriver.getDriver();
    public Actions actions = new Actions(driver);
    // private WebDriverWait wait = new WebDriverWait(driver,
    // Duration.ofSeconds(1));
    private CarEditPage carEditPage;

    @Before
    public void before() {
        this.carEditPage = new CarEditPage(driver);
    }

    @After
    public void after() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

    @Given("I am on the car edit page")
    public void iAmOnThecarEditPage() {
        carEditPage.get();
    }

}
