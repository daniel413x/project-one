package com.project_one_functional_tests.steps.carPageSteps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.project_one_functional_tests.pages.CarPage;
import com.project_one_functional_tests.utils.HeadlessChromeDriver;
import com.project_one_functional_tests.utils.ResetDatabase;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class CarPageSteps {

    private WebDriver driver = HeadlessChromeDriver.getDriver();
    private CarPage warehousesPage;
    private String testWarehouseName = "DC1";
    private String testMaxCapacity = "1800";
    private String testStreetAddress = "4000 Connecticut Avenue NW";
    private String testCity = "Washington";
    private String testState = "District of Columbia"; // Go DC Statehood
    private String testZipCode = "20009";

    @BeforeAll
    public static void resetDatabaseBeforeAll() {
        ResetDatabase.run();
    }

    @Before
    public void before() {
        this.warehousesPage = new CarPage(driver);
    }

    @After
    public void after() {
        if (this.driver != null) {
            this.driver.quit();
            ResetDatabase.run();
        }
    }

    @Given("I am on the car page")
    public void iAmOnTheWarehousesPage() {
        this.warehousesPage.get();
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

    // /**
    //  * 01-warehouses.feature
    //  * Scenario: Warehouse are shown 
    //  */

    // @Then("I should see warehouses")
    // public void iShouldSeeTheWarehouses() {
    //     assertTrue(this.warehousesPage.containsWarehouseWithName("CA1"));
    // }

    // /**
    //  * 02-warehouses-creation.feature
    //  * Scenario: A new warehouse is created
    //  */

    // @And("I have opened the create warehouse form modal")
    // public void iHaveOpenedTheCreateWarehouseFormModal() {
    //     this.warehousesPage.clickOnCreateWarehouseButton();
    // }

    // @When("I enter valid input for a new warehouse")
    // public void iEnterValidInput() {
    //     this.warehousesPage.enterFormInputs(testWarehouseName, testMaxCapacity, testStreetAddress, testCity, testState,
    //             testZipCode);
    // }

    // @And("I press the warehouse form submit button")
    // public void iPressTheSubmitButton() {
    //     this.warehousesPage.clickOnModalSubmitButton();
    // }

    // @Then("the created warehouse should appear in the list of warehouses")
    // public void theCreatedWarehouseShouldAppearInTheListOfWarehouses() {
    //     assertTrue(this.warehousesPage.containsWarehouseWithName(testWarehouseName));
    // }

    // /**
    //  * 03-warehouse-update.feature, 04-warehouse-delete.feature
    //  * Features: Update warehouse, Delete warehouse
    //  */

    // @And("I select the {string} icon on a warehouse card")
    // public void iSelectTheIconOnACard(String iconType) {
    //     this.warehousesPage.selectIconOnCard(0, iconType);
    // }

    // /**
    //  * 03-warehouse-update.feature
    //  * Scenarios: Save warehouse update,  Cancel warehouse update
    //  */

    // @Then("I should see a form with pre-filled fields of current warehouse information")
    // public void iShouldSeeWarehouseFormFieldsPrefilled() {
    //     assertTrue(this.warehousesPage.formFieldsContainCurrentWarehouseInformation());
    // }

    // @And("edit the Warehouse Name, Max Capacity, Street Address, City, State, and Zip Code fields")
    // public void editWarehouseNameMaxCapacityStreetAddressCityStateAndZipCode() {
    //     this.warehousesPage.editWarehouseNameMaxCapacityStreetAddressCityStateAndZipCode(testWarehouseName,
    //             testMaxCapacity, testStreetAddress, testCity, testState, testZipCode);
    // }

    // @Then("I should click the {string} button")
    // public void iShouldClickTheButton(String buttonText) {
    //     this.warehousesPage.clickButtonInModal(buttonText);
    // }

    // @And("see the warehouse name, city, and state updated")
    // public void seeWarehouseNameCityAndStateUpdated() {
    //     assertTrue(this.warehousesPage.savedCardIsUpdated(testWarehouseName, testCity, testState));
    // }

    // @And("see the warehouse name, city, and state unchanged")
    // public void seeWarehouseNameCityAndStateUnchanged() {
    //     assertTrue(this.warehousesPage.canceledCardIsNotUpdated());
    // }

    // @Then("I should select the updated warehouse card")
    // public void iShouldSelectUpdatedWarehouseCard() {
    //     this.warehousesPage.selectWarehouseCard(testWarehouseName);
    // }

    // @Then("I should select the unchanged warehouse card")
    // public void iShouldSelectUnchangedWarehouseCard() {
    //     this.warehousesPage.selectWarehouseCard();
    // }

    // @And("see the max capacity updated")
    // public void seeMaxCapacityUpdated() {
    //     assertTrue(this.warehousesPage.maxCapacityIsUpdated(testMaxCapacity));
    // }

    // @And("see the max capacity unchanged")
    // public void seeMaxCapacityUnchanged() {
    //     assertTrue(this.warehousesPage.maxCapacityIsNotUpdated());
    // }

    // /**
    //  * 04-warehouse-delete.feature
    //  * Scenarios: Delete warehouse
    //  */

    // @And("select Delete from the dropdown")
    // public void selectDeleteFromDropdown() {
    //     this.warehousesPage.selectDeleteDropdownOption();
    // }

    // @Then("I should not see the warehouse card displayed")
    // public void iShouldNotSeeWarehouseCardDisplayed() {
    //     assertTrue(this.warehousesPage.cardIsDeleted());
    // }

}
