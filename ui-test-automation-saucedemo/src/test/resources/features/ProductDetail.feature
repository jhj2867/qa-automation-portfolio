@ProductDetail
Feature: SauceDemo Product detail page

  Background: the user login
    Given the user login

  @TestCase-ProductDetail-01
  Scenario: the user can view product detail page
    Given the user can see main page
    When the user clicks on product "Sauce Labs Backpack"
    Then the product detail page shows "Sauce Labs Backpack"

  @TestCase-ProductDetail-02
  Scenario: the user can go back to products from detail page
    Given the user can see main page
    When the user clicks on product "Sauce Labs Backpack"
    Then the product detail page shows "Sauce Labs Backpack"
    When the user clicks the back to products button
    Then the user can see main page

  @TestCase-ProductDetail-03
  Scenario: the user can add product to cart from detail page
    Given the user can see main page
    When the user clicks on product "Sauce Labs Backpack"
    Then the product detail page shows "Sauce Labs Backpack"
    When the user adds the product to the cart from the detail page
    Then the cart button on the detail page shows "Remove"
    Then the cart badge shows "1"