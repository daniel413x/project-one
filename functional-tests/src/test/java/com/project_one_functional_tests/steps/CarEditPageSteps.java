package com.project_one_functional_tests.steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.project_one_functional_tests.pages.CarCreatePage;
import com.project_one_functional_tests.utils.HeadlessChromeDriver;
import com.project_one_functional_tests.utils.ResetDatabase;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class CarEditPageSteps {

    private WebDriver driver = HeadlessChromeDriver.getDriver();
    private CarCreatePage warehousesPage;
    private String testWarehouseName = "DC1";
    private String testMaxCapacity = "1800";
    private String testStreetAddress = "4000 Connecticut Avenue NW";
    private String testCity = "Washington";
    private String testState = "District of Columbia"; // Go DC Statehood
    private String testZipCode = "20009";
    private Actions actions = new Actions(driver);

    @Before
    public void before() {
        this.warehousesPage = new CarCreatePage(driver);
    }

    @After
    public void after() {
        if (this.driver != null) {
            this.driver.quit();
            ResetDatabase.run();
        }
    }

}
