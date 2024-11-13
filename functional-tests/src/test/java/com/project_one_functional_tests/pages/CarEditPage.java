package com.project_one_functional_tests.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.project_one_functional_tests.utils.HeadlessChromeDriver;

public class CarEditPage {

    WebDriver driver = HeadlessChromeDriver.getDriver();
    private static final String url = "http://localhost:3000/cars/1";
    // private String editedCardWarehouseName;
    // private String editedCardMaxCapacity;
    // private String editedCardCity;
    // private String editedCardState;
    // private String deletedCardWarehouseName;

    @FindBy(className = "ant-card")
    private List<WebElement> cards;

    @FindBy(className = "ant-btn-primary")
    private WebElement addWarehouseButton;

    @FindBy(className = "ant-modal-content")
    private WebElement modal;

    @FindBy(className = "ant-dropdown")
    private WebElement dropdown;

    public CarEditPage(WebDriver driver) {
        this.driver = HeadlessChromeDriver.getDriver();
    }

    // Verify if the current page URL matches the expected URL
    public boolean onPage() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.getCurrentUrl().equals(url);
    }

    // Navigate to the specified URL
    public void get() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.driver.get(url);
    }

    // // Check if the warehouse cards are loaded
    // public boolean warehouseCardsLoaded() {
    // try {
    // Thread.sleep(1000);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // return cards.size() > 0;
    // }

    // // Verify if all warehouses are displayed by checking related cards
    // public boolean iShouldSeeTheWarehouses() {
    // for (WebElement card : cards) {
    // String title =
    // card.findElement(By.className("ant-card-meta-title")).getText();
    // if (title.equals("CA1"))
    // return true;
    // }
    // return false;
    // }

    // // Click the button to create new warehouse
    // public void clickOnCreateWarehouseButton() {
    // addWarehouseButton.click();
    // modal.isDisplayed();
    // }

    // // Fill out form input in the modal
    // public void enterFormInputs(String warehouseName, String maxCapacity, String
    // streetAddress, String city,
    // String state, String zipCode) {
    // WebElement nameField = modal.findElement(By.id("form_in_modal_name"));
    // nameField.sendKeys(warehouseName);
    // WebElement maxCapacityField =
    // modal.findElement(By.id("form_in_modal_maxCapacity"));
    // maxCapacityField.sendKeys(maxCapacity);
    // WebElement streetAddressField =
    // modal.findElement(By.id("form_in_modal_streetAddress"));
    // streetAddressField.sendKeys(maxCapacity);
    // WebElement cityField = modal.findElement(By.id("form_in_modal_city"));
    // cityField.sendKeys(city);
    // WebElement stateField = modal.findElement(By.id("form_in_modal_state"));
    // stateField.sendKeys(state);
    // stateField.sendKeys(Keys.ENTER);
    // WebElement zipCodeField = modal.findElement(By.id("form_in_modal_zipCode"));
    // zipCodeField.sendKeys(zipCode);
    // }

    // // Click the submit button on the modal
    // public void clickOnModalSubmitButton() {
    // WebElement button = modal.findElement(By.xpath("//button[@type='submit']"));
    // button.click();
    // }

    // // Check if any cards contain matching name (returns true if found)
    // public boolean containsWarehouseWithName(String name) {
    // try {
    // Thread.sleep(1000);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // for (WebElement card : cards) {
    // String title =
    // card.findElement(By.className("ant-card-meta-title")).getText();
    // if (title.contains(name))
    // return true;
    // }
    // return false;
    // }

    // // Check if any cards contain matching name (returns false if found)
    // public boolean doesNotContainWarehouseWithName(String name) {
    // try {
    // Thread.sleep(1000);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // for (WebElement card : cards) {
    // String title =
    // card.findElement(By.className("ant-card-meta-title")).getText();
    // if (title.equals(name))
    // return false;
    // }
    // return true;
    // }

    // // Click on "Edit" or "..." button on card
    // public void selectIconOnCard(int index, String iconType) {
    // try {
    // Thread.sleep(1000);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }

    // switch (iconType) {
    // case "edit":
    // editedCardWarehouseName =
    // cards.get(index).findElement(By.className("ant-card-meta-title")).getText();
    // editedCardWarehouseName = editedCardWarehouseName.replace("Warehouse ", "");

    // String[] cityAndState =
    // cards.get(index).findElement(By.className("ant-card-meta-description"))
    // .getText()
    // .split(",");
    // editedCardCity = cityAndState[0].trim();
    // editedCardState = cityAndState[1].trim();

    // cards.get(index).findElement(By.className("anticon-edit")).click();
    // break;
    // case "...":
    // deletedCardWarehouseName =
    // cards.get(index).findElement(By.className("ant-card-meta-title")).getText();
    // deletedCardWarehouseName.replace("Warehouse ", "");

    // cards.get(index).findElement(By.className("anticon-ellipsis")).click();
    // break;
    // }
    // }

    // // Checks if the form field contains the current warehouse information based
    // on
    // // the initial values
    // public boolean formFieldsContainCurrentWarehouseInformation() {
    // WebElement nameField = modal.findElement(By.id("form_in_modal_name"));
    // WebElement cityField = modal.findElement(By.id("form_in_modal_city"));
    // WebElement stateField =
    // modal.findElement(By.className("ant-select-selection-item"));

    // return nameField.getAttribute("value").equals(editedCardWarehouseName) &&
    // cityField.getAttribute("value").equals(editedCardCity) &&
    // stateField.getAttribute("title").equals(StateConverter.getStateName(editedCardState));
    // }

    // // Edits the inputs in the form fields with the specified values, replacing
    // the
    // // existing inputs
    // public void
    // editWarehouseNameMaxCapacityStreetAddressCityStateAndZipCode(String
    // warehouseName, String maxCapacity,
    // String streetAddress, String city, String state, String zipCode) {
    // WebElement nameField = modal.findElement(By.id("form_in_modal_name"));

    // // Clear method won't work due to the way the initial value of the field is
    // // being set
    // for (int i = 0; i < editedCardWarehouseName.length(); i++) {
    // nameField.sendKeys(Keys.BACK_SPACE);
    // }

    // nameField.sendKeys(warehouseName);

    // WebElement maxCapacityField =
    // modal.findElement(By.id("form_in_modal_maxCapacity"));
    // editedCardMaxCapacity = maxCapacityField.getAttribute("value");

    // for (int i = 0; i < editedCardMaxCapacity.length(); i++) {
    // maxCapacityField.sendKeys(Keys.BACK_SPACE);
    // }

    // maxCapacityField.sendKeys(maxCapacity);

    // WebElement streetAddressField =
    // modal.findElement(By.id("form_in_modal_streetAddress"));
    // String editedCardStreetAddress = streetAddressField.getAttribute("value");

    // for (int i = 0; i < editedCardStreetAddress.length(); i++) {
    // streetAddressField.sendKeys(Keys.BACK_SPACE);
    // }

    // streetAddressField.sendKeys(streetAddress);

    // WebElement cityField = modal.findElement(By.id("form_in_modal_city"));

    // for (int i = 0; i < editedCardCity.length(); i++) {
    // cityField.sendKeys(Keys.BACK_SPACE);
    // }

    // cityField.sendKeys(city);

    // WebElement stateField = modal.findElement(By.id("form_in_modal_state"));
    // String editedCardStateFull = StateConverter.getStateName(editedCardState);

    // for (int i = 0; i < editedCardStateFull.length(); i++) {
    // stateField.sendKeys(Keys.BACK_SPACE);
    // }

    // stateField.sendKeys(state);
    // stateField.sendKeys(Keys.ENTER);

    // WebElement zipCodeField = modal.findElement(By.id("form_in_modal_zipCode"));
    // String editedCardZipCode = streetAddressField.getAttribute("value");

    // for (int i = 0; i < editedCardZipCode.length(); i++) {
    // zipCodeField.sendKeys(Keys.BACK_SPACE);
    // }

    // zipCodeField.sendKeys(zipCode);
    // }

    // // Click "Save" or "Cancel" button in form modal
    // public void clickButtonInModal(String buttonText) {
    // switch (buttonText) {
    // case "Save":
    // modal.findElement(By.xpath("//button[@type='submit']")).click();
    // break;
    // case "Cancel":
    // // Update frontend to be more specific selector
    // modal.findElement(By.className("ant-btn-default")).click();
    // break;
    // }
    // }

    // // Checks that card is updated after save by verifying it contains the new
    // // information
    // public boolean savedCardIsUpdated(String newWarehouseName, String newCity,
    // String newState) {
    // try {
    // Thread.sleep(1000);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }

    // for (WebElement card : cards) {
    // String title =
    // card.findElement(By.className("ant-card-meta-title")).getText();
    // String[] cityAndState =
    // card.findElement(By.className("ant-card-meta-description")).getText()
    // .split(",");
    // String city = cityAndState[0].trim();
    // String state = cityAndState[1].trim();

    // if (title.contains(newWarehouseName) && city.equals(newCity)
    // && state.equals(StateConverter.getStateAbbreviation(newState)))
    // return true;
    // }
    // return false;
    // }

    // // Checks that card is not updated after cancel by verifying it contains the
    // // initial information
    // public boolean canceledCardIsNotUpdated() {
    // try {
    // Thread.sleep(1000);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }

    // for (WebElement card : cards) {
    // String title =
    // card.findElement(By.className("ant-card-meta-title")).getText();
    // String[] cityAndState =
    // card.findElement(By.className("ant-card-meta-description")).getText()
    // .split(",");
    // String city = cityAndState[0].trim();
    // String state = cityAndState[1].trim();

    // if (title.contains(editedCardWarehouseName) && city.equals(editedCardCity)
    // && state.equals(editedCardState))
    // return true;
    // }
    // return false;
    // }

    // // Click card with specified warehouse name
    // public void selectWarehouseCard(String cardName) {
    // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // for (WebElement card : cards) {
    // wait.until(ExpectedConditions.visibilityOf(card));

    // String title =
    // card.findElement(By.className("ant-card-meta-title")).getText();

    // if (title.contains(cardName)) {
    // card.click();
    // break;
    // }
    // }
    // }

    // // Click the warehouse card that was edited
    // public void selectWarehouseCard() {
    // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // for (WebElement card : cards) {
    // wait.until(ExpectedConditions.visibilityOf(card));

    // String title =
    // card.findElement(By.className("ant-card-meta-title")).getText();

    // if (title.contains(editedCardWarehouseName)) {
    // card.click();
    // break;
    // }
    // }
    // }

    // // Verify max capacity is updated to the new max capacity
    // public boolean maxCapacityIsUpdated(String newMaxCapacity) {
    // try {
    // Thread.sleep(2000);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }

    // String h1 = driver.findElement(By.tagName("h1")).getText();

    // Pattern pattern = Pattern.compile("\\(\\d+/(\\d+)\\)");
    // Matcher matcher = pattern.matcher(h1);

    // if (matcher.find()) {
    // String maxCapacity = matcher.group(1);
    // return maxCapacity.equals(newMaxCapacity);
    // } else
    // return false;
    // }

    // // Verify max capacity is not updated and stays the initial max capacity
    // public boolean maxCapacityIsNotUpdated() {
    // try {
    // Thread.sleep(2000);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }

    // String h1 = driver.findElement(By.tagName("h1")).getText();

    // Pattern pattern = Pattern.compile("\\(\\d+/(\\d+)\\)");
    // Matcher matcher = pattern.matcher(h1);

    // if (matcher.find()) {
    // String maxCapacity = matcher.group(1);
    // return maxCapacity.equals(editedCardMaxCapacity);
    // } else
    // return false;
    // }

    // // Click "Delete" option in dropdown menu
    // public void selectDeleteDropdownOption() {
    // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
    // WebElement deleteMenuItem = wait
    // .until(ExpectedConditions.elementToBeClickable(By.className("ant-dropdown-menu-item-danger")));
    // deleteMenuItem.click();
    // }

    // // Checks that card is deleted by verifying no cards it contains the name
    // public boolean cardIsDeleted() {
    // return this.doesNotContainWarehouseWithName(deletedCardWarehouseName);
    // }
}
