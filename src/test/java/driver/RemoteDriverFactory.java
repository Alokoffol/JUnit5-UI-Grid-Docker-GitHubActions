package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.net.URL;

public class RemoteDriverFactory {

    private static final String GRID_URL = "http://localhost:4444";

    public static WebDriver createDriver(String browserName) {
        try {
            URL gridUrl = new URL(GRID_URL);

            if ("chrome".equalsIgnoreCase(browserName)) {
                // Используем ChromeOptionsConfig для Chrome в Grid
                ChromeOptions options = ChromeOptionsConfig.createChromeOptions(true);
                return new RemoteWebDriver(gridUrl, options);
            }

            if ("firefox".equalsIgnoreCase(browserName)) {
                FirefoxOptions options = new FirefoxOptions();
                return new RemoteWebDriver(gridUrl, options);
            }

            throw new IllegalArgumentException("Unknown browser: " + browserName);

        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to Selenium Grid. Run 'docker-compose up -d' first", e);
        }
    }
}