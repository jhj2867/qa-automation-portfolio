@MainPage
Feature: SauceDemo Main page

  Background: the user login
    Given the user login

  @TestCase-2
  Scenario: the user can see sidebar
    Given the user can see main page
    When the user click side bar button
    Then the user can see side bar area
    When the user click side bar close button
    Then side bar is hidden in main page

  @TestCase-3
  Scenario: the user can see about page
    Given the user can see main page
    When the user click side bar button
    Then the user can see side bar area
    When the user click About button
    Then the user can see "sourcelabs.com" page