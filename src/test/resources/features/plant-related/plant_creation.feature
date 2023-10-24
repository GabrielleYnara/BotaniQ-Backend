Feature: Create and Add Plant to a Garden

  Background:
    Given I am logged in

  Scenario: Successful plant creation and add it to a garden
#    Given I have a valid garden
    When I create a plant name "Basil" and type "Herb"
    Then the plant should be created successfully and added to the garden

  Scenario: Error when garden is invalid
    Given I provide an invalid garden
    When I create a plant name "Basil" and type "Herb"
    Then I should see an error message "Invalid garden"

  Scenario Outline: Error when plant information is missing
    Given I have a valid garden
    When I create a plant with "<name>" or "<type>" missing
    Then I should see an error message "Plant information is missing"

    Examples:
      | name  | type |
      |       | Herb |
      | Basil |      |
