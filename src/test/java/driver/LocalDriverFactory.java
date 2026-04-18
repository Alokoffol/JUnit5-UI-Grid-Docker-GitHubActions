package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class LocalDriverFactory {

    public static WebDriver createDriver(String browserName) {
        // Проверяем, запущены ли тесты в CI (GitHub Actions)
        boolean isCI = "true".equals(System.getenv("CI"));
        boolean headless = isCI || Boolean.parseBoolean(System.getProperty("headless", "false"));

        if ("chrome".equalsIgnoreCase(browserName)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            if (headless) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
            }
            return new ChromeDriver(options);

        } else if ("firefox".equalsIgnoreCase(browserName)) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            if (headless) {
                options.addArguments("--headless");
            }
            return new FirefoxDriver(options);

        } else {
            throw new IllegalArgumentException("Unknown browser: " + browserName);
        }
    }
}