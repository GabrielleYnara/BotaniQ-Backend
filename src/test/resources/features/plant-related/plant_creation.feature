Feature: Create and Add Plant to a Garden

  Background:
    Given I am logged in

  Scenario: Successful plant creation and add it to a garden
    Given I have a valid garden
    When I create a plant name "Basil" and type "Herb"
    Then the plant should be created successfully and added to the garden
