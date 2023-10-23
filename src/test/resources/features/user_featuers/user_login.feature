Feature: User Login

  Scenario: Successful login
    Given I am already registered
    When I provide a valid email and password pair
    Then I should be redirected to the home page

  Scenario: Login with invalid credentials
    Given I am already registered
    When I provide an incorrect email or password
    Then I should see a failed login message