package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.webdrivermanager.ChromeOptionsFactory;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = ChromeOptionsFactory.create();
        driver.set(new ChromeDriver(options));
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            // quit() can throw if the browser already crashed or is stuck on a
            // blocking dialog; without the finally, the ThreadLocal never clears
            // and the chromedriver process is left orphaned for failed scenarios
            try {
                currentDriver.quit();
            } finally {
                driver.remove();
            }
        }
    }
}