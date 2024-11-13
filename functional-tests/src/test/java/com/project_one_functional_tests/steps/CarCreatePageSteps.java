package com.project_one_functional_tests.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.project_one_functional_tests.pages.CarCreatePage;
import com.project_one_functional_tests.utils.HeadlessChromeDriver;
import com.project_one_functional_tests.utils.ResetDatabase;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CarCreatePageSteps {

    private WebDriver driver = HeadlessChromeDriver.getDriver();
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
    private CarCreatePage carCreatePage;
    private String testWarehouseName = "DC1";
    private String testMaxCapacity = "1800";
    private String testStreetAddress = "4000 Connecticut Avenue NW";
    private String testCity = "Washington";
    private String testState = "District of Columbia"; // Go DC Statehood
    private String testZipCode = "20009";
    private Actions actions = new Actions(driver);

    @BeforeAll
    public static void resetDatabaseBeforeAll() {
        ResetDatabase.run();
    }

    @Before
    public void before() {
        this.carCreatePage = new CarCreatePage(driver);
    }

    @After
    public void after() {
        if (this.driver != null) {
            this.driver.quit();
            ResetDatabase.run();
        }
    }

    @Given("I am on the car create page")
    public void iAmOnTheCarCreatePage() {
        this.carCreatePage.get();
    }

    @Then("the make dropdown will be focused")
    public void iShouldBeAbleToFocusTheMakeDropdown() {
        WebElement makeDropdown = driver.findElement(By.cssSelector("[data-testid='make-dropdown-trigger']"));

        // currently focused element
        WebElement focusedElement = driver.switchTo().activeElement();

        assertEquals(makeDropdown, focusedElement);
    }

    @And("I have opened the make dropdown")
    public void iHaveOpenedTheMakeDropdown() {
        WebElement makeDropdown = driver.findElement(By.cssSelector("[data-testid='make-dropdown-trigger']"));

        makeDropdown.click();
    }

    @Then("I should see the third item in the list highlighted")
    public void iShouldSeeTheThirdItemInTheListHighlighted() {
        WebElement thirdItem = driver.findElement(By.cssSelector("[data-testid='make-2']"));

        // currently focused element
        WebElement focusedElement = driver.switchTo().activeElement();

        assertEquals(thirdItem, focusedElement);
    }

    @Then("I should see the fourth item in the list highlighted")
    public void iShouldSeeTheFourthItemInTheListHighlighted() {
        WebElement fourthItem = driver.findElement(By.cssSelector("[data-testid='make-4']"));

        // currently focused element
        WebElement focusedElement = driver.switchTo().activeElement();

        assertEquals(fourthItem, focusedElement);
    }

    @Then("the search input should be focused")
    public void theSearchInputShouldBeFocused() {
        WebElement fourthItem = driver.findElement(By.cssSelector("[data-testid='make-dropdown-search-input']"));

        // currently focused element
        WebElement focusedElement = driver.switchTo().activeElement();

        assertEquals(fourthItem, focusedElement);
    }

    @Then("I should see an item in the list highlighted")
    public void iShouldSeeAnItemInTheListHighlighted() {
        WebElement anItem = driver.findElement(By.cssSelector("[data-testid='make-0']"));

        // currently focused element
        WebElement focusedElement = driver.switchTo().activeElement();

        assertEquals(anItem, focusedElement);
    }

    // press tab 10 times to try and find the element with the passed in test id
    // string
    private void tabTo(String testId) {
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
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='New car created']")));
        } catch (NoSuchElementException e) {
            System.out.println(e);
        }
    }

    // /**
    // * 01-warehouses.feature
    // * Scenario: Warehouse are shown
    // */

    // @Then("I should see warehouses")
    // public void iShouldSeeTheWarehouses() {
    // assertTrue(this.carCreatePage.containsWarehouseWithName("CA1"));
    // }

    // /**
    // * 02-warehouses-creation.feature
    // * Scenario: A new warehouse is created
    // */

    // @And("I have opened the create warehouse form modal")
    // public void iHaveOpenedTheCreateWarehouseFormModal() {
    // this.carCreatePage.clickOnCreateWarehouseButton();
    // }

    // @When("I enter valid input for a new warehouse")
    // public void iEnterValidInput() {
    // this.carCreatePage.enterFormInputs(testWarehouseName, testMaxCapacity,
    // testStreetAddress, testCity, testState,
    // testZipCode);
    // }

    // @And("I press the warehouse form submit button")
    // public void iPressTheSubmitButton() {
    // this.carCreatePage.clickOnModalSubmitButton();
    // }

    // @Then("the created warehouse should appear in the list of warehouses")
    // public void theCreatedWarehouseShouldAppearInTheListOfWarehouses() {
    // assertTrue(this.carCreatePage.containsWarehouseWithName(testWarehouseName));
    // }

    // /**
    // * 03-warehouse-update.feature, 04-warehouse-delete.feature
    // * Features: Update warehouse, Delete warehouse
    // */

    // @And("I select the {string} icon on a warehouse card")
    // public void iSelectTheIconOnACard(String iconType) {
    // this.carCreatePage.selectIconOnCard(0, iconType);
    // }

    // /**
    // * 03-warehouse-update.feature
    // * Scenarios: Save warehouse update, Cancel warehouse update
    // */

    // @Then("I should see a form with pre-filled fields of current warehouse
    // information")
    // public void iShouldSeeWarehouseFormFieldsPrefilled() {
    // assertTrue(this.carCreatePage.formFieldsContainCurrentWarehouseInformation());
    // }

    // @And("edit the Warehouse Name, Max Capacity, Street Address, City, State, and
    // Zip Code fields")
    // public void editWarehouseNameMaxCapacityStreetAddressCityStateAndZipCode() {
    // this.carCreatePage.editWarehouseNameMaxCapacityStreetAddressCityStateAndZipCode(testWarehouseName,
    // testMaxCapacity, testStreetAddress, testCity, testState, testZipCode);
    // }

    // @Then("I should click the {string} button")
    // public void iShouldClickTheButton(String buttonText) {
    // this.carCreatePage.clickButtonInModal(buttonText);
    // }

    // @And("see the warehouse name, city, and state updated")
    // public void seeWarehouseNameCityAndStateUpdated() {
    // assertTrue(this.carCreatePage.savedCardIsUpdated(testWarehouseName,
    // testCity, testState));
    // }

    // @And("see the warehouse name, city, and state unchanged")
    // public void seeWarehouseNameCityAndStateUnchanged() {
    // assertTrue(this.carCreatePage.canceledCardIsNotUpdated());
    // }

    // @Then("I should select the updated warehouse card")
    // public void iShouldSelectUpdatedWarehouseCard() {
    // this.carCreatePage.selectWarehouseCard(testWarehouseName);
    // }

    // @Then("I should select the unchanged warehouse card")
    // public void iShouldSelectUnchangedWarehouseCard() {
    // this.carCreatePage.selectWarehouseCard();
    // }

    // @And("see the max capacity updated")
    // public void seeMaxCapacityUpdated() {
    // assertTrue(this.carCreatePage.maxCapacityIsUpdated(testMaxCapacity));
    // }

    // @And("see the max capacity unchanged")
    // public void seeMaxCapacityUnchanged() {
    // assertTrue(this.carCreatePage.maxCapacityIsNotUpdated());
    // }

    // /**
    // * 04-warehouse-delete.feature
    // * Scenarios: Delete warehouse
    // */

    // @And("select Delete from the dropdown")
    // public void selectDeleteFromDropdown() {
    // this.carCreatePage.selectDeleteDropdownOption();
    // }

    // @Then("I should not see the warehouse card displayed")
    // public void iShouldNotSeeWarehouseCardDisplayed() {
    // assertTrue(this.carCreatePage.cardIsDeleted());
    // }

}
