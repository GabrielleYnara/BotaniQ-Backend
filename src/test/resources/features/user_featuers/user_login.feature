Feature: User Login

  Scenario: Successful login
    Given I am a registered user
    When I provide a valid email and password pair
    Then I should be authorized

  Scenario: Login with invalid credentials
    Given I am a registered user
    When I provide an incorrect email or password
    Then I should see a failed login message