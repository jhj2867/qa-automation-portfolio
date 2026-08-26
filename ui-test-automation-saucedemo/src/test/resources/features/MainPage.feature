@MainPage
Feature: SauceDemo Main page

  Background: the user login
    Given the user login

  @TestCase-Main-01
  Scenario: the user can see sidebar
    Given the user can see main page
    When the user click side bar button
    Then the user can see side bar area
    When the user click side bar close button
    Then side bar is hidden in main page

  @TestCase-Main-02
  Scenario: the user can see about page
    Given the user can see main page
    When the user click side bar button
    Then the user can see side bar area
    When the user click About button
    Then the user can see "saucelabs.com" page

  @TestCase-Main-03
  Scenario: the user can sort products by price low to high
    Given the user can see main page
    When the user sorts products by "Price (low to high)"
    Then the products are sorted by "price low to high"

  @TestCase-Main-04
  Scenario: the user can sort products by price high to low
    Given the user can see main page
    When the user sorts products by "Price (high to low)"
    Then the products are sorted by "price high to low"

  @TestCase-Main-05
  Scenario: the user can sort products by name Z to A
    Given the user can see main page
    When the user sorts products by "Name (Z to A)"
    Then the products are sorted by "name Z to A"

  @TestCase-Main-06
  Scenario: the user can add and remove a product from the cart
    Given the user can see main page
    When the user adds "Sauce Labs Backpack" to the cart
    Then the cart badge shows "1"
    When the user removes "Sauce Labs Backpack" from the cart
    Then there is no cart badge

  @TestCase-Main-07
  Scenario: Reset App State clears the cart badge
    Given the user can see main page
    When the user adds "Sauce Labs Backpack" to the cart
    Then the cart badge shows "1"
    When the user click side bar button
    Then the user can see side bar area
    When the user click reset app state button
    Then there is no cart badge

  @TestCase-Main-08
  Scenario: the user can logout
    Given the user can see main page
    When the user click side bar button
    Then the user can see side bar area
    When the user click logout button
    Then the user is back on the login page