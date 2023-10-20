Feature: View Garden Plants

  Background:
    Given I am logged in
    And I have a valid garden

  Scenario: Successful retrieval of garden plants
    When I request to view the plants in the garden
    Then I should see a list of plants associated with the garden
