Feature: Garden Creation

  Background:
    Given I am logged in

  Scenario: Successful garden creation with a unique description
    Given I provide a unique description "Deck Vertical Garden"
    And I can provide additional notes "This is a replica of my real garden"
    When I attempt to create the garden
    Then the garden should be saved successfully

  Scenario: Error when the garden description is not unique
    Given a garden with description "My Existing Garden" already exists
    And I provide the description "My Existing Garden" for the new garden
    When I attempt to create the garden
    Then I should see an error message "A garden with that description already exists"