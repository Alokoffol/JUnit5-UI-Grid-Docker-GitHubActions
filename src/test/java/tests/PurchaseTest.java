package tests;

import base.TestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.*;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;


import static org.junit.jupiter.api.Assertions.*;

@Feature("Полный путь покупки")
public class PurchaseTest extends TestBase {

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox"})
    @DisplayName("Полный путь покупки")
    @Story("Полный путь покупки")
    public void testCompletePurchase(String browser) {
        initDriver(browser);

        logStep("1. Логин");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver);
        assertEquals("Products", productsPage.getPageTitle(), "Не удалось войти");

        logStep("2. Добавляем товар в корзину");
        productsPage.addFirstProductToCart();
        assertEquals(1, productsPage.getCartItemCount(), "Товар не добавился в корзину");

        logStep("3. Переходим в корзину");
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        assertTrue(cartPage.isPageLoaded(), "Страница корзины не загрузилась");
        assertEquals(1, cartPage.getItemCount(), "Некорректное количество товаров в корзине");

        logStep("4. Начинаем оформление");
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillCheckoutInfo("John", "Doe", "12345");

        logStep("5. Подтверждаем заказ");
        assertTrue(checkoutPage.isOverviewLoaded(), "Страница подтверждения не загрузилась");
        checkoutPage.clickFinish();

        logStep("6. Проверяем успешное завершение");
        assertEquals("Thank you for your order!", checkoutPage.getCompleteHeader(),
                "Заказ не был успешно оформлен");

        logStep("✅ Покупка успешно завершена");
    }
}