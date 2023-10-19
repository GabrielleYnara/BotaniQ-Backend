Feature: User Login

  Scenario: Successful login
    When I provide a valid email and password pair
    Then I should be redirected to the home page

  Scenario: Login with invalid credentials
    When I provide an incorrect email or password
    Then I should see an error message