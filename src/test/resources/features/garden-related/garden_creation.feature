Feature: Garden Creation

#  Background:
#    Given I am logged in

  Scenario: Successful garden creation with just a description
    Given I provide a description "Deck Vertical Garden"
    And no other garden has the description "Deck Vertical Garden"
    When I attempt to create a garden just with a description
    Then the garden should be saved successfully

  Scenario: Successful garden creation with a description and additional notes
    Given I provide a description "Deck Vertical Garden"
    And I provide additional notes "This is a replica of my real garden"
    And no other garden has the description "Deck Vertical Garden"
    When I attempt to create a garden with a description and notes
    Then the garden should be saved successfully

  Scenario: Error when the garden description is not unique
    Given a garden with description "Deck Vertical Garden" already exists
    And I provide a description "Deck Vertical Garden"
    When I attempt to create a duplicated garden
    Then I should see an error message "A garden with that description already exists"