Feature: Profile Update

  Background:
    Given I am logged in

  Scenario Outline: Successful profile update
    When I enter my first name "<firstName>", last name "<lastName>", and or bio "<bio>"
    Then The profile is updated and I see a success message

    Examples:
    | firstName | lastName |        bio         |
    | Test      |          |                    |
    |           | User     | Some Bio           |
    | Gabrielle | Ynara    | Something about me |

  Scenario: Profile update failure due to server error
    Given I am logged in
    When the server encounters an error
    Then I should see the profile update failed