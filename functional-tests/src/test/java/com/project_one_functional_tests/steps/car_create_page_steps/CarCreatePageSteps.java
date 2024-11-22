package com.project_one_functional_tests.steps.car_create_page_steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.project_one_functional_tests.pages.CarCreatePage;
import com.project_one_functional_tests.utils.HeadlessChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CarCreatePageSteps {

    WebDriver driver = HeadlessChromeDriver.getDriver();
    public Actions actions = new Actions(driver);
    // private WebDriverWait wait = new WebDriverWait(driver,
    // Duration.ofSeconds(1));
    private CarCreatePage carCreatePage;

    @Before
    public void before() {
        this.carCreatePage = new CarCreatePage(driver);
    }

    @After
    public void after() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

    @Given("I am on the car create page")
    public void iAmOnTheCarCreatePage() {
        carCreatePage.get();
    }

    @Then("the make dropdown will be focused")
    public void iShouldBeAbleToFocusTheMakeDropdown() {
        WebElement makeDropdown = carCreatePage.getMakeDropdown();

        // currently focused element
        WebElement focusedElement = driver.switchTo().activeElement();

        assertEquals(makeDropdown, focusedElement);
    }

    @And("I have opened the make dropdown")
    public void iHaveOpenedTheMakeDropdown() {
        carCreatePage.openMakeDropdown();
    }

    @Then("I should see the third item in the list highlighted")
    public void iShouldSeeTheThirdItemInTheListHighlighted() {
        WebElement thirdItem = carCreatePage.getMake2();

        // currently focused element
        WebElement focusedElement = driver.switchTo().activeElement();

        assertEquals(thirdItem, focusedElement);
    }

    @Then("I should see the fourth item in the list highlighted")
    public void iShouldSeeTheFourthItemInTheListHighlighted() {
        WebElement fourthItem = carCreatePage.getMake4();

        // currently focused element
        WebElement focusedElement = driver.switchTo().activeElement();

        assertEquals(fourthItem, focusedElement);
    }

    @Then("the search input should be focused")
    public void theSearchInputShouldBeFocused() {
        carCreatePage.theSearchInputShouldBeFocused();
    }

    @Then("I should see an item in the list highlighted")
    public void iShouldSeeAnItemInTheListHighlighted() {
        WebElement anItem = carCreatePage.getMake0();

        // currently focused element
        WebElement focusedElement = driver.switchTo().activeElement();

        assertEquals(anItem, focusedElement);
    }

    // press tab 10 times to try and find the element with the passed in test id
    // string
    public void tabTo(String testId) {
        String selector = "[data-testid='" + testId + "']";
        WebElement itemToWhichToTab = driver.findElement(By.cssSelector(selector));
        WebElement focusedElement = driver.switchTo().activeElement();
        ;
        for (int i = 0; i < 10; i += 1) {
            if (itemToWhichToTab.equals(focusedElement)) {
                break;
            }
            actions.sendKeys(Keys.TAB).pause(Duration.ofMillis(200)).perform();
            focusedElement = driver.switchTo().activeElement();
        }

        assertEquals(itemToWhichToTab, focusedElement);
    }

    @When("I fill out the car form using only the keyboard")
    public void iFillOutTheCarFormUsingOnlyTheKeyboard() {
        tabTo("make-dropdown-trigger");
        actions.sendKeys(Keys.ENTER);
        actions.sendKeys(Keys.ENTER);
        tabTo("model-dropdown-trigger");
        actions.sendKeys(Keys.ENTER);
        actions.sendKeys(Keys.ENTER);
        tabTo("color-dropdown-trigger");
        actions.sendKeys(Keys.ENTER);
        actions.sendKeys(Keys.ENTER);
        tabTo("owner-dropdown-trigger");
        actions.sendKeys(Keys.ENTER);
        actions.sendKeys(Keys.ENTER);
        tabTo("vin-input");
        actions.sendKeys("123456789010");
        tabTo("year-input");
        actions.sendKeys("2024");
        tabTo("price-input");
        actions.sendKeys("10000");
        tabTo("mileage-input");
        actions.sendKeys("100");
        tabTo("insurance-policy-number-input");
        actions.sendKeys("123456789010");
        tabTo("insurance-expiration-input");
        actions.sendKeys("01/01/2025");
        tabTo("registration-number-input");
        actions.sendKeys("123456789010");
        tabTo("registration-expiration-input");
        actions.sendKeys("01/01/2025");
        tabTo("last-maintenance-date-input");
        actions.sendKeys("01/01/2024");
        tabTo("form-submit-button");
        actions.sendKeys(Keys.ENTER);
    }

    @When("I press the submit button using only the keyboard")
    public void iPressTheSubmitButtonUsingOnlyTheKeyboard() {
        tabTo("make-dropdown-trigger");

    }

    @Then("the form should have been submitted successfully")
    public void theFormShouldHaveBeenSubmittedSuccessfully() {
        carCreatePage.newCarCreatedNotificationAppeared();
    }

}
