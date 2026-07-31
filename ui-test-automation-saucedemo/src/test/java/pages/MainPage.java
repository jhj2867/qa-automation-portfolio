package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {

    private final WebDriver driver;

    private static final By SIDE_BAR_BUTTON = By.xpath("//div[@class='bm-burger-button']");
    private static final By SIDE_BAR_CLOSE_BUTTON = By.xpath("//button[@class='bm-cross-button']");
    private static final By SIDE_BAR_HIDDEN = By.xpath("//div[@class='bm-menu-wrap'][@hidden='true']");
    private static final By ABOUT_BUTTON = By.xpath("//div[@class='bm-menu-wrap' and not(@hidden='true')]//a[@id='about_sidebar_link']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickSideBarButton() {
        driver.findElement(SIDE_BAR_BUTTON).click();
    }

    public void clickSideBarCloseButton() {
        driver.findElement(SIDE_BAR_CLOSE_BUTTON).click();
    }

    public boolean isSideBarHidden() {
        return !driver.findElements(SIDE_BAR_HIDDEN).isEmpty();
    }

    public void clickAboutButton() {
        String originalWindow = driver.getWindowHandle();
        driver.findElement(ABOUT_BUTTON).click();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public String getCurrentPageUrl() {
        return driver.getCurrentUrl();
    }
}