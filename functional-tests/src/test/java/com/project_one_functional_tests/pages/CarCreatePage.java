package com.project_one_functional_tests.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.project_one_functional_tests.utils.HeadlessChromeDriver;

public class CarCreatePage {

    WebDriver driver = HeadlessChromeDriver.getDriver();
    private Actions actions = new Actions(driver);
    AxeBuilder axeBuilder = new AxeBuilder();
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
    Results axeResults;
    private static final String url = "http://localhost:3000/cars/create";

    @FindBy(css = "[data-testid='make-dropdown-trigger']")
    private WebElement makeDropdown;

    @FindBy(css = "[data-testid='make-0']")
    private WebElement make0;

    @FindBy(css = "[data-testid='make-2']")
    private WebElement make2;

    @FindBy(css = "[data-testid='make-4']")
    private WebElement make4;

    @FindBy(css = "[data-testid='make-dropdown-search-input']")
    private WebElement makeDropdownSearchInput;

    public CarCreatePage(WebDriver driver) {
        this.driver = HeadlessChromeDriver.getDriver();
        PageFactory.initElements(driver, this);
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

    public WebElement getMakeDropdown() {
        return makeDropdown;
    }

    public WebElement getMake0() {
        return make0;
    }

    public WebElement getMake2() {
        return make2;
    }

    public WebElement getMake4() {
        return make4;
    }

    public void theSearchInputShouldBeFocused() {
        WebElement searchInput = driver.findElement(By.cssSelector("[data-testid='make-dropdown-search-input']"));

        // currently focused element
        WebElement focusedElement = driver.switchTo().activeElement();

        assertEquals(searchInput, focusedElement);
    }

    public void newCarCreatedNotificationAppeared() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='New car created']")));
        } catch (NoSuchElementException e) {
            System.out.println(e);
        }
    }

    public void openMakeDropdown() {
        makeDropdown.click();
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
}
