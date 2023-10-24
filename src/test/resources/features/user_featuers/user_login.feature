Feature: User Login

  Background:
    Given I am a registered user

  Scenario: Successful login
    When I provide a valid email and password pair
    Then I should be authorized

  Scenario: Login with invalid credentials
    When I provide an incorrect email or password
    Then I should see a failed login message