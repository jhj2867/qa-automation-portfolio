@Checkout
Feature: SauceDemo Checkout flow

  Background: the user login
    Given the user login

  @TestCase-Checkout-01
  Scenario: the user can complete checkout with valid information
    Given the user can see main page
    When the user adds "Sauce Labs Backpack" to the cart
    When the user clicks the cart icon
    Then the user can see the cart page
    When the user clicks the checkout button
    Then the user can see the checkout information page
    When the user fills in checkout information with first name "Hyun", last name "Jo" and postal code "12345"
    When the user clicks the checkout continue button
    Then the user can see the checkout overview page
    And the checkout total shows "Total: $32.39"
    When the user clicks the finish button
    Then the user can see the checkout complete page
    And there is no cart badge

  @TestCase-Checkout-02
  Scenario: checkout information requires first name
    Given the user can see main page
    When the user adds "Sauce Labs Backpack" to the cart
    When the user clicks the cart icon
    Then the user can see the cart page
    When the user clicks the checkout button
    Then the user can see the checkout information page
    When the user fills in checkout information with first name "", last name "" and postal code ""
    When the user clicks the checkout continue button
    Then the user can see checkout error msg "Error: First Name is required"

  @TestCase-Checkout-03
  Scenario: checkout information requires last name
    Given the user can see main page
    When the user adds "Sauce Labs Backpack" to the cart
    When the user clicks the cart icon
    Then the user can see the cart page
    When the user clicks the checkout button
    Then the user can see the checkout information page
    When the user fills in checkout information with first name "Hyun", last name "" and postal code ""
    When the user clicks the checkout continue button
    Then the user can see checkout error msg "Error: Last Name is required"

  @TestCase-Checkout-04
  Scenario: checkout information requires postal code
    Given the user can see main page
    When the user adds "Sauce Labs Backpack" to the cart
    When the user clicks the cart icon
    Then the user can see the cart page
    When the user clicks the checkout button
    Then the user can see the checkout information page
    When the user fills in checkout information with first name "Hyun", last name "Jo" and postal code ""
    When the user clicks the checkout continue button
    Then the user can see checkout error msg "Error: Postal Code is required"

  @TestCase-Checkout-05
  Scenario: the user can cancel checkout information step back to the cart page
    Given the user can see main page
    When the user adds "Sauce Labs Backpack" to the cart
    When the user clicks the cart icon
    Then the user can see the cart page
    When the user clicks the checkout button
    Then the user can see the checkout information page
    When the user clicks the checkout cancel button
    Then the user can see the cart page

  @TestCase-Checkout-06
  Scenario: the user can cancel checkout overview step back to the main page
    Given the user can see main page
    When the user adds "Sauce Labs Backpack" to the cart
    When the user clicks the cart icon
    Then the user can see the cart page
    When the user clicks the checkout button
    Then the user can see the checkout information page
    When the user fills in checkout information with first name "Hyun", last name "Jo" and postal code "12345"
    When the user clicks the checkout continue button
    When the user clicks the checkout cancel button
    Then the user can see main page

  @TestCase-Checkout-07
  Scenario: the user can complete checkout with an empty cart
    Given the user can see main page
    When the user clicks the cart icon
    Then the user can see the cart page
    When the user clicks the checkout button
    Then the user can see the checkout information page
    When the user fills in checkout information with first name "Hyun", last name "Jo" and postal code "12345"
    When the user clicks the checkout continue button
    Then the checkout total shows "Total: $0.00"
