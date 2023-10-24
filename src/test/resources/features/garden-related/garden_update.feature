Feature: Garden Information Update
  Background:
    Given I am logged in

  Scenario Outline: Successful garden update
    When I update the description to "<description>" and the notes to "<notes>"
    Then the garden should be updated successfully

    Examples:
      | description | notes     |
      | New Garden  |           |
      |             | New Notes |
      | New Garden  | New Notes |
