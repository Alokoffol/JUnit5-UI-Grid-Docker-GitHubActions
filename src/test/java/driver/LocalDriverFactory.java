package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class LocalDriverFactory {

    public static WebDriver createDriver(String browserName) {

        // Приводим название к нижнему регистру, чтобы "Chrome", "CHROME" и "chrome" работали одинаково
        String browser = browserName.toLowerCase();

        if ("chrome".equals(browser)) {
            // Настраиваем драйвер Chrome
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = ChromeOptionsConfig.createChromeOptions(false);
            return new ChromeDriver(options);

        } else if ("firefox".equals(browser)) {
            // Настраиваем драйвер Firefox
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            // Здесь можно добавить специфичные настройки для Firefox, если нужно
            return new FirefoxDriver(options);

        } else {
            // Если передан неизвестный браузер
            throw new IllegalArgumentException("Неподдерживаемый браузер: " + browserName);
        }
    }
}