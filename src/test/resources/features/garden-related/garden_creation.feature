Feature: Garden Creation

  Background:
    Given I am logged in

  Scenario Outline: Successful garden creation
    When I attempt to create a garden with a description "<description>" and notes "<notes>"
    Then the garden should be saved successfully

    Examples:
      |     description      |                 notes               |
      | Deck Vertical Garden | This is a replica of my real garden |
      | Living room plants   |                                     |

  Scenario: Failed garden description is not unique
    Given a garden with description "Deck Vertical Garden" already exists
    When I attempt to create a garden with a description "Deck Vertical Garden" and notes "Some notes"
    Then I should see an error message "A garden with that description already exists"