package com.project_one_functional_tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.project_one_functional_tests.utils.HeadlessChromeDriver;

public class CarsPage {

    WebDriver driver = HeadlessChromeDriver.getDriver();
    AxeBuilder axeBuilder = new AxeBuilder();
    Results axeResults;
    private static final String url = System.getProperty("clientUrl", "http://localhost:3000") + "/cars";

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

    public CarsPage(WebDriver driver) {
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
}
