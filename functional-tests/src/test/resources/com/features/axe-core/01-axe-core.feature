@axe-core-car-edit-page
Feature: axe-core results for CarEditPage

  Scenario: Axecore generates results
    Given I am on the route "<route>"
    When I assess the axe-core violations labeled "<route>" in Extent reports
    Then there should be no violations

    Examples:
      | route          |
      | /cars          |
      | /cars/1        |
      | /cars/create   |
      | /makes         |
      | /makes/1       |
      | /makes/create  |
      | /models        |
      | /models/1      |
      | /models/create |
      | /owners        |
      | /owners/1      |
      | /owners/create |
