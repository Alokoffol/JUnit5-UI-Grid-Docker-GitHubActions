package driver;

import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

// Настройки хрома
public class ChromeOptionsConfig {

    public static ChromeOptions createChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        /*
        Решение проблемы с открывающимся окном
         */
        // Отключаем проверку утечек паролей
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_leak_detection.enabled", false);
        prefs.put("safebrowsing.enabled", false);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        options.setExperimentalOption("prefs", prefs);

        // Добавляем необходимые аргументы
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-password-manager-reauthentication");
        options.addArguments("--disable-features=PasswordLeakDetection");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--incognito");

        // Добавляем стандартные аргументы
        options.addArguments("--disable-search-engine-choice-screen");
        options.addArguments("--disable-infobars");

        // Читаем HEADLESS из environment переменной
        boolean headlessFromEnv = Boolean.parseBoolean(System.getenv("HEADLESS"));
        boolean isHeadless = headless || headlessFromEnv;

        if (isHeadless) {
            options.addArguments("--headless=new");  // новый headless режим
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        return options;
    }
}