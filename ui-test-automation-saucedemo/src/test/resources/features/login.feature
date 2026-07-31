@Login
Feature: SauceDemo Login

  @TestCase-1
  Scenario: Successful login with standard user
    Given the user is on the SauceDemo login page
    When the user logs in with username "standard_user" and password "secret_sauce"
    Then the user should see the inventory page
