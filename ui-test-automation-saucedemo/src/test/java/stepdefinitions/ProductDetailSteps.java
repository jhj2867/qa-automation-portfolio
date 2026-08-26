package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.ProductDetailPage;
import utils.DriverFactory;

public class ProductDetailSteps {

    private ProductDetailPage productDetailPage;

    @Then("the product detail page shows {string}")
    public void the_product_detail_page_shows(String expectedName) {
        productDetailPage = new ProductDetailPage(DriverFactory.getDriver());
        Assertions.assertEquals(expectedName, productDetailPage.getItemName());
    }

    @When("the user clicks the back to products button")
    public void the_user_clicks_the_back_to_products_button() {
        productDetailPage.clickBackToProducts();
    }

    @When("the user adds the product to the cart from the detail page")
    public void the_user_adds_the_product_to_the_cart_from_the_detail_page() {
        productDetailPage.clickCartButton();
    }

    @Then("the cart button on the detail page shows {string}")
    public void the_cart_button_on_the_detail_page_shows(String expectedText) {
        Assertions.assertEquals(expectedText, productDetailPage.getCartButtonText());
    }
}