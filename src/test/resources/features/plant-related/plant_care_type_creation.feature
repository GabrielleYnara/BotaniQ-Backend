Feature: Create Descriptive Care Types for Plants

  Background:
    Given I am logged in

  Scenario: Successfully creating a new care type
    When I attempt to create the care type with a "water" description set the frequency to "daily"
    Then the application should save the care type and frequency
    And I should see the record created and success message

  Scenario: Error when trying to create a duplicated care type
    When I attempt to create the care type with a "Trimming" description set the frequency to "4 weeks"
    Then the application should not save the care type
