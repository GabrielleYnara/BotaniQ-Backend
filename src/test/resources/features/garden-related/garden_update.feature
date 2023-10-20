Feature: Garden Information Update
  Background:
    Given I am logged in
    And I have a garden with description "Old Garden" and notes "Old Notes"

  Scenario Outline: Successful garden update
    When I update the description to "<description>" and the notes to "<notes>"
    And the new information is different from the original
    Then the garden should be updated successfully
    And I should see a success message "Garden information updated successfully"

    Examples:
      | description | notes     |
      | New Garden  |           |
      |             | New Notes |
      | New Garden  | New Notes |

  Scenario: Update failure with unchanged information
    When I update the garden with the same description and notes
    Then I should see an error message "Garden information update failed"
