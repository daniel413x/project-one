@axecore
Feature: Axecore Results

  Scenario: Axecore generates results
    Given I am on the car create page
    When I assess axeResults
    Then violations should be less than 1
