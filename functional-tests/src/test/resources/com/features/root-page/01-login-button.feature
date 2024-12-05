@root-page
Feature: Login Button

  Scenario: Clicking the login button takes the user to the dashboard
    Given I am on the root page
    When I click the login button
    Then I should be taken to the dashboard
