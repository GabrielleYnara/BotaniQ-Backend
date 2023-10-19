Feature: User Registration

  Scenario: Successful registration
    When I enter a unique email and password
    Then I should see a success message

  Scenario: Registration with an already taken email
    When I enter an email and password
    Then I should see an error message