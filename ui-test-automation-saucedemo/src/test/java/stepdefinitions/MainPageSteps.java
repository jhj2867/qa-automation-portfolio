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
}