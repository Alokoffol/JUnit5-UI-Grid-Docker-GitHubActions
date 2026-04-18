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

        // Определяем, запущены ли тесты в CI (GitHub Actions)
        boolean isCI = "true".equals(System.getenv("CI"));

        if ("chrome".equals(browser)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = ChromeOptionsConfig.createChromeOptions(isCI);
            return new ChromeDriver(options);

        } else if ("firefox".equals(browser)) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();

            if (isCI) {
                // В CI — headless режим
                options.addArguments("--headless");
                options.addArguments("--no-sandbox");
                WebDriver driver = new FirefoxDriver(options);
                driver.manage().window().setSize(new Dimension(1920, 1080));
                return driver;
            } else {
                // Локально — обычный режим с GUI
                return new FirefoxDriver(options);
            }

        } else {
            throw new IllegalArgumentException("Unknown browser: " + browserName);
        }
    }
}