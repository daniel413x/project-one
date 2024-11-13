@car-create-page
Feature: Dropdown Accessibility

  Scenario: Dropdowns are tab-accessible
    Given I am on the car create page
    When I press the tab key 8 times
    Then the make dropdown will be focused

  Scenario: Dropdown items are navigable via the down arrow key
    Given I am on the car create page
    And I have opened the make dropdown
    When I press the arrow key down 2 times
    Then I should see the third item in the list highlighted

  Scenario: Dropdown items are navigable via the up arrow key
    Given I am on the car create page
    And I have opened the make dropdown
    When I press the arrow key up 2 times
    Then I should see the fourth item in the list highlighted

  Scenario: Pressing tab with a dropdown opened switches focus to the search input
    Given I am on the car create page
    And I have opened the make dropdown
    When I press the tab key once
    Then the search input should be focused

  Scenario: Pressing tab with a dropdown opened and the search input focused switches focus to the dropdown items
    Given I am on the car create page
    And I have opened the make dropdown
    And I press the tab key 2 times
    Then I should see an item in the list highlighted
