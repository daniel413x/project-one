@axe-core-car-create-page
Feature: axe-core results for CarCreatePage

  Scenario: Axecore generates results
    Given I am on the car create page
    When I assess the axe-core violations labeled "CreateCarPage" in Extent reports
    Then there should be no violations
