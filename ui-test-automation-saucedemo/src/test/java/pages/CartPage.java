package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    private final WebDriver driver;

    private static final By CART_ITEMS = By.className("cart_item");
    private static final By CHECKOUT_BUTTON = By.id("checkout");
    private static final By CONTINUE_SHOPPING_BUTTON = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getItemCount() {
        return driver.findElements(CART_ITEMS).size();
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