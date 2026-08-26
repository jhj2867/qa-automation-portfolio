package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.LoginPage;
import utils.DriverFactory;

public class LoginSteps {

    private LoginPage loginPage;

    @Given("the user is on the SauceDemo login page")
    public void the_user_is_on_the_saucedemo_login_page() {
        loginPage = new LoginPage(DriverFactory.getDriver());
    }

    @When("the user logs in with username {string} and password {string}")
    public void the_user_logs_in_with_username_and_password(String username, String password) {
        loginPage.login(username, password);
    }

    @Then("the user should see the inventory page")
    public void the_user_should_see_the_inventory_page() {
        Assertions.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("inventory.html"));
    }

    @Then("the user can see error msg {string} in login page")
    public void the_user_can_see_error_msg_in_login_page(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, loginPage.getErrorMessage());
    }
}