package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By FIRST_NAME_INPUT = By.id("first-name");
    private static final By LAST_NAME_INPUT = By.id("last-name");
    private static final By POSTAL_CODE_INPUT = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");
    private static final By CANCEL_BUTTON = By.id("cancel");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");
    private static final By FINISH_BUTTON = By.id("finish");
    private static final By SUMMARY_TOTAL_LABEL = By.className("summary_total_label");
    private static final By COMPLETE_HEADER = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitUntilStepOneLoaded() {
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
    }

    public void fillInformation(String firstName, String lastName, String postalCode) {
        driver.findElement(FIRST_NAME_INPUT).sendKeys(firstName);
        driver.findElement(LAST_NAME_INPUT).sendKeys(lastName);
        driver.findElement(POSTAL_CODE_INPUT).sendKeys(postalCode);
    }

    public void clickContinue() {
        driver.findElement(CONTINUE_BUTTON).click();
    }

    public void clickCancel() {
        driver.findElement(CANCEL_BUTTON).click();
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
        return driver.findElement(ERROR_MESSAGE).getText();
    }

    public void waitUntilStepTwoLoaded() {
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
    }

    public String getTotalLabel() {
        return driver.findElement(SUMMARY_TOTAL_LABEL).getText();
    }

    public void clickFinish() {
        driver.findElement(FINISH_BUTTON).click();
    }

    public void waitUntilCompleteLoaded() {
        wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
    }

    public String getCompleteHeader() {
        return driver.findElement(COMPLETE_HEADER).getText();
    }
}