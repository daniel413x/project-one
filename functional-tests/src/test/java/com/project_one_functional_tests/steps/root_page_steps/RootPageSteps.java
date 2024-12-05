package com.project_one_functional_tests.steps.root_page_steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.project_one_functional_tests.pages.RootPage;
import com.project_one_functional_tests.utils.HeadlessChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RootPageSteps {

    WebDriver driver = HeadlessChromeDriver.getDriver();
    public Actions actions = new Actions(driver);
    private RootPage rootPage;

    @Before
    public void before() {
        this.rootPage = new RootPage(driver);
    }

    @After
    public void after() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

    @Given("I am on the root page")
    public void iAmOnTheRootPage() {
        rootPage.get();
    }

    @When("I click the login button")
    public void iPressTheSubmitButtonUsingOnlyTheKeyboard() {
        rootPage.clickOnLoginButton();
    }

    @Then("I should be taken to the dashboard")
    public void iShouldBeTakenToTheDashboard() {
        rootPage.iShouldBeTakenToTheDashboard();
    }
}
