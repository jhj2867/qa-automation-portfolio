package utils.webdrivermanager;

import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeOptionsFactory {

    public static ChromeOptions create() {
        ChromeOptions options = new ChromeOptions();

        if (isHeadless()) {
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        return options;
    }

    private static boolean isHeadless() {
        return System.getenv("CI") != null;
    }
}
