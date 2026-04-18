package tests;

import base.TestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.LoginPage;
import pages.ProductsPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;


import static org.junit.jupiter.api.Assertions.*;

@Feature("Логин")
public class LoginTests extends TestBase {

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Успешный логин")
    @Story("Успешный логин")
    public void testSuccessfulLogin(String browser) {
        initDriver(browser);

        logStep("Тест успешного логина");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver);
        assertEquals("Products", productsPage.getPageTitle(), "Заголовок страницы не соответствует");

        logStep("Успешный вход выполнен");
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Неверный логин")
    @Story("Неверный логин")
    public void testInvalidLogin(String browser) {
        initDriver(browser);

        logStep("Тест с неверными данными");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wrong_user", "wrong_pass");

        assertTrue(loginPage.isErrorMessageDisplayed(), "Сообщение об ошибке не отобразилось");
        assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                "Текст ошибки не соответствует");

        logStep("Ошибка отобразилась корректно");
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Пустые поля логин и пароль")
    @Story("Пустые поля")
    public void testEmptyFields(String browser) {
        initDriver(browser);

        logStep("Тест с пустыми полями");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLogin();

        assertTrue(loginPage.isErrorMessageDisplayed(), "Сообщение об ошибке не отобразилось");
        assertTrue(loginPage.getErrorMessage().contains("Username is required"),
                "Текст ошибки не соответствует");

        logStep("Ошибка отобразилась корректно");
    }
}