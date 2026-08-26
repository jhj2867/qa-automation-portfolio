package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;
import utils.DriverFactory;

public class Hooks {

    @Before
    public void setUp() {
        DriverFactory.initDriver();
        DriverFactory.getDriver().get(ConfigReader.get("base.url"));
    }

    @After
    public void tearDown(Scenario scenario) {
        dismissAnyOpenAlert();
        takeScreenshot(scenario);
        DriverFactory.quitDriver();
    }

    // a failed step can leave a JS alert/confirm open, which blocks WebDriver
    // commands (including quit()) until it's dismissed
    private void dismissAnyOpenAlert() {
        WebDriver driver = DriverFactory.getDriver();
        if (driver == null) {
            return;
        }
        try {
            driver.switchTo().alert().dismiss();
        } catch (NoAlertPresentException ignored) {
        }
    }

    // attaches a screenshot of the final page state to the scenario, so it
    // shows up alongside the steps in both the Cucumber html report and Allure
    private void takeScreenshot(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        if (driver == null) {
            return;
        }
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", scenario.getName());
    }
}