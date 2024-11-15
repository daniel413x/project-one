@axe-core-car-edit-page
Feature: axe-core results for CarEditPage

  Scenario: Axecore generates results
    Given I am on the car edit page
    When I assess the axe-core violations labeled "EditCarPage" in Extent reports
    Then there should be no violations
