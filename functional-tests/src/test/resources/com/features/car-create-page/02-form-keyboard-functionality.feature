@car-create-page
Feature: Form Keyboard Functionality

  Scenario: The form can be completed using only the keyboard
    Given I am on the car create page
    When I fill out the car form using only the keyboard
    And I press the submit button using only the keyboard
    Then the form should have been submitted successfully
