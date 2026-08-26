package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.CartPage;
import utils.DriverFactory;

public class CartSteps {

    private CartPage cartPage;

    @Then("the user can see the cart page")
    public void the_user_can_see_the_cart_page() {
        cartPage = new CartPage(DriverFactory.getDriver());
        Assertions.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("cart.html"));
    }

    @Then("the cart page shows {int} item(s)")
    public void the_cart_page_shows_items(int expectedCount) {
        Assertions.assertEquals(expectedCount, cartPage.getItemCount());
    }

    @When("the user removes {string} from the cart page")
    public void the_user_removes_from_the_cart_page(String productName) {
        cartPage.removeItem(productName);
    }

    @When("the user clicks the checkout button")
    public void the_user_clicks_the_checkout_button() {
        cartPage.clickCheckout();
    }

    @When("the user clicks the continue shopping button")
    public void the_user_clicks_the_continue_shopping_button() {
        cartPage.clickContinueShopping();
    }
}