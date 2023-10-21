Feature: Create Descriptive Care Types for Plants

  Background:
    Given I am logged in

  Scenario: Successfully creating a new care type
    Given I provide a "water" description for the care type
    And I set the frequency to "daily"
    When I attempt to create the care type
    Then the application should save the care type and frequency
    And I should see a success message

  Scenario: Error when trying to create a care type
    Given I provide an existing "water" description for the care type
    And I set the frequency to "daily"
    When I attempt to create the care type
    Then the application should not save the care type
    And I should see an error message
