@Cart
Feature: SauceDemo Cart page

  Background: the user login
    Given the user login

  @TestCase-Cart-01
  Scenario: the user can view items added to the cart
    Given the user can see main page
    When the user adds "Sauce Labs Backpack" to the cart
    And the user adds "Sauce Labs Bike Light" to the cart
    When the user clicks the cart icon
    Then the user can see the cart page
    And the cart page shows 2 items

  @TestCase-Cart-02
  Scenario: the user can remove an item from the cart page
    Given the user can see main page
    When the user adds "Sauce Labs Backpack" to the cart
    When the user clicks the cart icon
    Then the user can see the cart page
    When the user removes "Sauce Labs Backpack" from the cart page
    Then the cart page shows 0 items
    And there is no cart badge

  @TestCase-Cart-03
  Scenario: the user can continue shopping from the cart page
    Given the user can see main page
    When the user clicks the cart icon
    Then the user can see the cart page
    When the user clicks the continue shopping button
    Then the user can see main page

  @TestCase-Cart-04
  Scenario: the user can proceed to checkout from the cart page
    Given the user can see main page
    When the user adds "Sauce Labs Backpack" to the cart
    When the user clicks the cart icon
    Then the user can see the cart page
    When the user clicks the checkout button
    Then the user can see the checkout information page