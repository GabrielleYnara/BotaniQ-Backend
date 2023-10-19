Feature: Profile Update

  Scenario: Successful profile update
    Given I am logged in
    When I can enter my first name, last name, and/or bio
    Then the aplication should update the infromation and show a sucess message

  Scenario: Profile update failure due to server error
    Given I am logged in
    When the server encounters an error
    Then I should see an error message "Profile update failed"