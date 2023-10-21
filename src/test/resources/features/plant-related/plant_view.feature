Feature: View Singular Plant
  Background:
    Given I am logged in
    And I have a valid garden

  Scenario: Successful plant retrieval
    Given I provide a valid plant
    When I request to view a singular plant in the garden
    Then I should see the plants details

