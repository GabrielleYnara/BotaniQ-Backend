Feature: Profile Update

  Scenario: Successful profile update
    Given I am logged in
    When I enter my first name, last name, and or bio
    Then The personal information is updated and display success message

  Scenario: Profile update failure due to server error
    Given I am logged in
    When the server encounters an error
    Then I should see an error message "Profile update failed"