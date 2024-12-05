package com.project_one_functional_tests.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.project_one_functional_tests.utils.HeadlessChromeDriver;

public class RootPage {

    WebDriver driver = HeadlessChromeDriver.getDriver();
    private static final String url = System.getProperty("clientUrl", "http://localhost:3000");

    @FindBy(id = "login-button")
    private WebElement loginButton;

    public RootPage(WebDriver driver) {
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

    public WebElement getLoginButton() {
        return loginButton;
    }

    public void clickOnLoginButton() {
        loginButton.click();
    }

    public void iShouldBeTakenToTheDashboard() {
        assertEquals(driver.getCurrentUrl(), url + "/dashboard");
    }
}
