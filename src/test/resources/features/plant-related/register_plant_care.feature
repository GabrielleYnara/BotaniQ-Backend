Feature: Register Care for a Specific Plant

  Background:
    Given I am logged in
    And I have a valid garden
    And I provide a valid plant
    And I provide a valid care type

  Scenario: Successfully registering care for a plant
    Given I specify the date care was administered
    When I request to register the care event
    Then the application should save the care event
    And I should see a success message

