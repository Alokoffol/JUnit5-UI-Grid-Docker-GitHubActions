package base;

import driver.LocalDriverFactory;
import driver.RemoteDriverFactory;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import static constants.AppConstants.BASE_URL;

public class TestBase {

    protected WebDriver driver;

    @BeforeEach
    void setUp() {
        // Пустой, так как инициализация теперь в тесте
    }

    protected void initDriver(String browserName) {
        if (driver != null) {
            driver.quit();
        }

        boolean useGrid = Boolean.parseBoolean(System.getProperty("useGrid", "false"));
        String browser = System.getProperty("browser", "chrome");

        if (useGrid) {
            driver = RemoteDriverFactory.createDriver(browser);
        } else {
            driver = LocalDriverFactory.createDriver(browser);
        }

        driver.get(BASE_URL);
        driver.manage().window().maximize();
        System.out.println("🚀 Запуск в браузере: " + browserName);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Step("{stepName}")
    protected void logStep(String stepName) {
        System.out.println("▶ " + stepName);
    }
}