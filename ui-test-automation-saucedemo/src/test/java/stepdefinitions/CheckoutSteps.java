package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.CheckoutPage;
import utils.DriverFactory;

public class CheckoutSteps {

    private CheckoutPage checkoutPage;

    @Then("the user can see the checkout information page")
    public void the_user_can_see_the_checkout_information_page() {
        checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        Assertions.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("checkout-step-one.html"));
    }

    @When("the user fills in checkout information with first name {string}, last name {string} and postal code {string}")
    public void the_user_fills_in_checkout_information(String firstName, String lastName, String postalCode) {
        checkoutPage.fillInformation(firstName, lastName, postalCode);
    }

    @When("the user clicks the checkout continue button")
    public void the_user_clicks_the_checkout_continue_button() {
        checkoutPage.clickContinue();
    }

    @When("the user clicks the checkout cancel button")
    public void the_user_clicks_the_checkout_cancel_button() {
        checkoutPage.clickCancel();
    }

    @Then("the user can see checkout error msg {string}")
    public void the_user_can_see_checkout_error_msg(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, checkoutPage.getErrorMessage());
    }

    @Then("the user can see the checkout overview page")
    public void the_user_can_see_the_checkout_overview_page() {
        Assertions.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("checkout-step-two.html"));
    }

    @Then("the checkout total shows {string}")
    public void the_checkout_total_shows(String expectedTotal) {
        Assertions.assertEquals(expectedTotal, checkoutPage.getTotalLabel());
    }

    @When("the user clicks the finish button")
    public void the_user_clicks_the_finish_button() {
        checkoutPage.clickFinish();
    }

    @Then("the user can see the checkout complete page")
    public void the_user_can_see_the_checkout_complete_page() {
        Assertions.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("checkout-complete.html"));
        Assertions.assertEquals("Thank you for your order!", checkoutPage.getCompleteHeader());
    }
}