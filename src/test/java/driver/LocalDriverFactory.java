package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class LocalDriverFactory {

    public static WebDriver createDriver(String browserName) {
        String browser = browserName.toLowerCase();

        if ("chrome".equals(browser)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = ChromeOptionsConfig.createChromeOptions(false);
            // Для CI важно добавить аргументы, если их нет в конфиге
            // options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
            return new ChromeDriver(options);

        } else if ("firefox".equals(browser)) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();

            // ВАЖНО: В GitHub Actions (Linux) Firefox должен быть headless
            options.addArguments("--headless");

            // Дополнительно можно увеличить таймаут старта, так как в CI все медленнее
            // options.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.EAGER);

            return new FirefoxDriver(options);

        } else {
            throw new IllegalArgumentException("Unknown browser: " + browserName);
        }
    }
}