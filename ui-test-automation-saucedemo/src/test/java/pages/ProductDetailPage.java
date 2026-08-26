package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailPage {

    private final WebDriver driver;

    private static final By ITEM_NAME = By.className("inventory_details_name");
    private static final By BACK_BUTTON = By.id("back-to-products");
    private static final By CART_BUTTON = By.cssSelector(".inventory_details_desc_container button");

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getItemName() {
        return driver.findElement(ITEM_NAME).getText();
    }

    public void clickBackToProducts() {
        driver.findElement(BACK_BUTTON).click();
    }

    public void clickCartButton() {
        driver.findElement(CART_BUTTON).click();
    }

    public String getCartButtonText() {
        return driver.findElement(CART_BUTTON).getText();
    }
}