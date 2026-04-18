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

        if ("chrome".equals(browser)) {
            WebDriverManager.chromedriver().setup();
            // Используем твой конфиг, он отличный
            ChromeOptions options = ChromeOptionsConfig.createChromeOptions(false);
            return new ChromeDriver(options);

        } else if ("firefox".equals(browser)) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();

            // ВАЖНО: Только headless. Никаких --width/--height!
            options.addArguments("--headless");

            // Для стабильности в Linux/CI можно добавить:
            options.addArguments("--no-sandbox");

            WebDriver driver = new FirefoxDriver(options);

            // Явно задаем размер окна для Firefox, так как аргументы не работают
            // Это предотвратит проблемы с версткой в headless режиме
            driver.manage().window().setSize(new Dimension(1920, 1080));

            return driver;

        } else {
            throw new IllegalArgumentException("Unknown browser: " + browserName);
        }
    }
}