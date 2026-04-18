package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class LocalDriverFactory {

    public static WebDriver createDriver(String browserName) {
        String browser = browserName.toLowerCase();

        // Определяем headless режим
        boolean isCI = "true".equals(System.getenv("CI"));
        boolean headless = isCI || Boolean.parseBoolean(System.getProperty("headless", "false"));

        if ("chrome".equals(browser)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = ChromeOptionsConfig.createChromeOptions(headless);

            // Критические настройки для headless в CI
            if (headless) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
            }

            return new ChromeDriver(options);

        } else if ("firefox".equals(browser)) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();

            if (headless) {
                options.addArguments("--headless");
                options.addArguments("--no-sandbox");
            }

            WebDriver driver = new FirefoxDriver(options);
            if (headless) {
                driver.manage().window().setSize(new Dimension(1920, 1080));
            }
            return driver;

        } else {
            throw new IllegalArgumentException("Unknown browser: " + browserName);
        }
    }
}