package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By SIDE_BAR_BUTTON = By.xpath("//div[@class='bm-burger-button']");
    private static final By SIDE_BAR_CLOSE_BUTTON = By.id("react-burger-cross-btn");
    private static final By SIDE_BAR_HIDDEN = By.xpath("//div[@class='bm-menu-wrap'][@hidden='true']");
    private static final By ABOUT_BUTTON = By.xpath("//div[@class='bm-menu-wrap' and not(@hidden='true')]//a[@id='about_sidebar_link']");
    private static final By RESET_APP_STATE_LINK = By.id("reset_sidebar_link");
    private static final By LOGOUT_LINK = By.id("logout_sidebar_link");
    private static final By INVENTORY_ITEM_IMAGES = By.cssSelector(".inventory_item_img img");
    private static final By SORT_DROPDOWN = By.cssSelector("[data-test='product-sort-container']");
    private static final By ITEM_NAMES = By.className("inventory_item_name");
    private static final By ITEM_PRICES = By.className("inventory_item_price");
    private static final By CART_ICON = By.className("shopping_cart_link");
    private static final By CART_BADGE = By.className("shopping_cart_badge");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void clickSideBarButton() {
        driver.findElement(SIDE_BAR_BUTTON).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(ABOUT_BUTTON));
        // the sidebar slides in over a 0.5s CSS transition; wait it out so clicks
        // land on the menu's final position instead of a mid-animation offset
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // react-burger-menu's sidebar links don't respond to Selenium's native mouse-event
    // click (verified via debug run: correct element, correct position, no exception,
    // just no effect) but do respond to a real DOM click() call, so sidebar links are
    // clicked through JavaScript instead of WebElement.click().
    private void clickViaJs(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void clickSideBarCloseButton() {
        clickViaJs(driver.findElement(SIDE_BAR_CLOSE_BUTTON));
        wait.until(ExpectedConditions.presenceOfElementLocated(SIDE_BAR_HIDDEN));
    }

    public boolean isSideBarHidden() {
        return !driver.findElements(SIDE_BAR_HIDDEN).isEmpty();
    }

    public void clickAboutButton() {
        clickViaJs(driver.findElement(ABOUT_BUTTON));
    }

    public void clickResetAppState() {
        clickViaJs(driver.findElement(RESET_APP_STATE_LINK));
    }

    public void clickLogout() {
        clickViaJs(driver.findElement(LOGOUT_LINK));
    }

    public String getCurrentPageUrl() {
        return driver.getCurrentUrl();
    }

    public boolean allProductImagesAreBroken() {
        List<WebElement> images = driver.findElements(INVENTORY_ITEM_IMAGES);
        return !images.isEmpty() && images.stream().allMatch(img -> img.getAttribute("src").contains("sl-404"));
    }

    public void selectSortOption(String visibleText) {
        new Select(driver.findElement(SORT_DROPDOWN)).selectByVisibleText(visibleText);
    }

    private List<String> getProductNames() {
        return driver.findElements(ITEM_NAMES).stream().map(WebElement::getText).toList();
    }

    private List<Double> getProductPrices() {
        return driver.findElements(ITEM_PRICES).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .toList();
    }

    public boolean isSortedByPriceAscending() {
        List<Double> prices = getProductPrices();
        List<Double> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);
        return prices.equals(sorted);
    }

    public boolean isSortedByPriceDescending() {
        List<Double> prices = getProductPrices();
        List<Double> sorted = new ArrayList<>(prices);
        sorted.sort(Collections.reverseOrder());
        return prices.equals(sorted);
    }

    public boolean isSortedByNameAscending() {
        List<String> names = getProductNames();
        List<String> sorted = new ArrayList<>(names);
        Collections.sort(sorted);
        return names.equals(sorted);
    }

    public boolean isSortedByNameDescending() {
        List<String> names = getProductNames();
        List<String> sorted = new ArrayList<>(names);
        sorted.sort(Collections.reverseOrder());
        return names.equals(sorted);
    }

    private By productButtonByName(String productName) {
        return By.xpath("//div[contains(@class, 'inventory_item_name') and text()='" + productName + "']"
                + "/ancestor::div[@class='inventory_item']//button");
    }

    public void clickProductName(String productName) {
        driver.findElement(By.xpath("//div[contains(@class, 'inventory_item_name') and text()='" + productName + "']")).click();
    }

    public void toggleProductInCart(String productName) {
        driver.findElement(productButtonByName(productName)).click();
    }

    public void clickCartIcon() {
        driver.findElement(CART_ICON).click();
    }

    public String getCartBadgeText() {
        List<WebElement> badges = driver.findElements(CART_BADGE);
        return badges.isEmpty() ? null : badges.get(0).getText();
    }
}