package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By CART_ITEMS = By.className("cart_item");
    private static final By CHECKOUT_BUTTON = By.id("checkout");
    private static final By CONTINUE_SHOPPING_BUTTON = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitUntilLoaded() {
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }

    public int getItemCount() {
        return driver.findElements(CART_ITEMS).size();
    }

    public void waitUntilItemCount(int expectedCount) {
        wait.until(ExpectedConditions.numberOfElementsToBe(CART_ITEMS, expectedCount));
    }

    public void removeItem(String productName) {
        driver.findElement(By.xpath("//div[@class='cart_item'][.//div[contains(@class, 'inventory_item_name') and text()='"
                + productName + "']]//button")).click();
    }

    public void clickCheckout() {
        driver.findElement(CHECKOUT_BUTTON).click();
    }

    public void clickContinueShopping() {
        driver.findElement(CONTINUE_SHOPPING_BUTTON).click();
    }
}