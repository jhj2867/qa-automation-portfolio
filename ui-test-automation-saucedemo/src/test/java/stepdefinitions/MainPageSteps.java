package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.LoginPage;
import pages.MainPage;
import utils.DriverFactory;

public class MainPageSteps {

    private MainPage mainPage;

    @Given("the user login")
    public void the_user_login() {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.login("standard_user", "secret_sauce");
    }

    @Given("the user can see main page")
    public void the_user_can_see_main_page() {
        mainPage = new MainPage(DriverFactory.getDriver());
        mainPage.waitUntilLoaded();
        Assertions.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("inventory.html"));
    }

    @When("the user click side bar button")
    public void the_user_click_side_bar_button() {
        mainPage.clickSideBarButton();
    }

    @Then("the user can see side bar area")
    public void the_user_can_see_side_bar_area() {
        Assertions.assertFalse(mainPage.isSideBarHidden());
    }

    @When("the user click side bar close button")
    public void the_user_click_side_bar_close_button() {
        mainPage.clickSideBarCloseButton();
    }

    @Then("side bar is hidden in main page")
    public void side_bar_is_hidden_in_main_page() {
        Assertions.assertTrue(mainPage.isSideBarHidden());
    }

    @When("the user click About button")
    public void the_user_click_about_button() {
        mainPage.clickAboutButton();
    }

    @Then("the user can see {string} page")
    public void the_user_can_see_page(String expectedDomain) {
        String currentUrl = mainPage.getCurrentPageUrl();
        Assertions.assertTrue(currentUrl.contains(expectedDomain));
    }

    @Then("the user can see broken product images in main page")
    public void the_user_can_see_broken_product_images_in_main_page() {
        MainPage page = new MainPage(DriverFactory.getDriver());
        Assertions.assertTrue(page.allProductImagesAreBroken());
    }

    @When("the user sorts products by {string}")
    public void the_user_sorts_products_by(String visibleText) {
        mainPage.selectSortOption(visibleText);
    }

    @Then("the products are sorted by {string}")
    public void the_products_are_sorted_by(String sortType) {
        boolean sorted = switch (sortType) {
            case "price low to high" -> mainPage.isSortedByPriceAscending();
            case "price high to low" -> mainPage.isSortedByPriceDescending();
            case "name A to Z" -> mainPage.isSortedByNameAscending();
            case "name Z to A" -> mainPage.isSortedByNameDescending();
            default -> throw new IllegalArgumentException("Unknown sort type: " + sortType);
        };
        Assertions.assertTrue(sorted);
    }

    @When("the user adds {string} to the cart")
    public void the_user_adds_to_the_cart(String productName) {
        mainPage.toggleProductInCart(productName);
    }

    @When("the user removes {string} from the cart")
    public void the_user_removes_from_the_cart(String productName) {
        mainPage.toggleProductInCart(productName);
    }

    @Then("the cart badge shows {string}")
    public void the_cart_badge_shows(String expectedCount) {
        Assertions.assertEquals(expectedCount, mainPage.getCartBadgeText());
    }

    @Then("there is no cart badge")
    public void there_is_no_cart_badge() {
        Assertions.assertNull(mainPage.getCartBadgeText());
    }

    @When("the user clicks on product {string}")
    public void the_user_clicks_on_product(String productName) {
        mainPage.clickProductName(productName);
    }

    @When("the user clicks the cart icon")
    public void the_user_clicks_the_cart_icon() {
        mainPage.clickCartIcon();
    }

    @When("the user click reset app state button")
    public void the_user_click_reset_app_state_button() {
        mainPage.clickResetAppState();
    }

    @When("the user click logout button")
    public void the_user_click_logout_button() {
        mainPage.clickLogout();
    }

    @Then("the user is back on the login page")
    public void the_user_is_back_on_the_login_page() {
        Assertions.assertEquals("https://www.saucedemo.com/", DriverFactory.getDriver().getCurrentUrl());
    }
}