@Login
Feature: SauceDemo Login

  @TestCase-Login-01
  Scenario: Successful login with standard user
    Given the user is on the SauceDemo login page
    When the user logs in with username "standard_user" and password "secret_sauce"
    Then the user should see the inventory page

  @TestCase-Login-02
  Scenario: Fail login with standard user (Username and password are not matching)
    Given the user is on the SauceDemo login page
    When the user logs in with username "standard_user" and password "secret_sauce1"
    Then the user can see error msg "Epic sadface: Username and password do not match any user in this service" in login page

  @TestCase-Login-03
  Scenario: Fail login with locked out user
    Given the user is on the SauceDemo login page
    When the user logs in with username "locked_out_user" and password "secret_sauce"
    Then the user can see error msg "Epic sadface: Sorry, this user has been locked out." in login page

  @TestCase-Login-04
  Scenario: Login with problem user shows broken product images
    Given the user is on the SauceDemo login page
    When the user logs in with username "problem_user" and password "secret_sauce"
    Then the user should see the inventory page
    And the user can see broken product images in main page

  @TestCase-Login-05
  Scenario: Fail login with empty username
    Given the user is on the SauceDemo login page
    When the user logs in with username "" and password "secret_sauce"
    Then the user can see error msg "Epic sadface: Username is required" in login page

  @TestCase-Login-06
  Scenario: Fail login with empty password
    Given the user is on the SauceDemo login page
    When the user logs in with username "standard_user" and password ""
    Then the user can see error msg "Epic sadface: Password is required" in login page


  @TestCase-Login-07
  Scenario: Login with error user cannot remove item from cart
    Given the user is on the SauceDemo login page
    When the user logs in with username "error_user" and password "secret_sauce"
    Then the user can see main page
    When the user adds "Sauce Labs Backpack" to the cart
    Then the cart badge shows "1"
    When the user removes "Sauce Labs Backpack" from the cart
    Then the cart badge shows "1"
