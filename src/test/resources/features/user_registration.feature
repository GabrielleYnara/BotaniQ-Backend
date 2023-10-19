Feature: User Registration

  Scenario: Successful registration
    Given I am on the registration page
    When I enter a unique email and password
    Then I should see a success message

  Scenario: Registration with an already taken email
    Given I am on the registration page
    When I enter an email and password
    Then I should see an error message