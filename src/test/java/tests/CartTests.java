package tests;

import base.TestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Корзина")
public class CartTests extends TestBase {

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Добавление товара в корзину")
    @Story("Добавление товара")
    public void testAddProductToCart(String browser) {
        initDriver(browser);

        logStep("1. Логин");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver);
        assertEquals(productsPage.getPageTitle(), "Products");

        logStep("2. Добавляем товар в корзину");
        productsPage.addFirstProductToCart();
        assertEquals(1, productsPage.getCartItemCount(), "Счётчик корзины не обновился");

        logStep("3. Переходим в корзину и проверяем");
        productsPage.goToCart();
        CartPage cartPage = new CartPage(driver);

        assertTrue(cartPage.isPageLoaded(), "Страница корзины не загрузилась");
        assertEquals(1, cartPage.getItemCount(), "Товар не появился в корзине");

        logStep("✅ Товар успешно добавлен в корзину");
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Удаление товара из корзины")
    @Story("Удаление товара")
    public void testRemoveProductFromCart(String browser) {
        initDriver(browser);

        logStep("1. Логин и добавление товара");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addFirstProductToCart();
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        assertEquals(1, cartPage.getItemCount(), "Товар не добавился");

        logStep("2. Удаляем товар");
        cartPage.removeFirstItem();

        logStep("3. Проверяем, что корзина пуста");
        assertTrue(cartPage.isCartEmpty(), "Корзина должна быть пустой после удаления");

        logStep("✅ Товар успешно удалён из корзины");
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Продолжить покупки из корзины")
    @Story("Продолжить покупки")
    public void testContinueShopping(String browser) {
        initDriver(browser);

        logStep("1. Логин и добавление товара");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addFirstProductToCart();
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        assertTrue(cartPage.isPageLoaded());

        logStep("2. Нажимаем 'Continue Shopping'");
        cartPage.clickContinueShopping();

        logStep("3. Проверяем, что вернулись на страницу товаров");
        assertEquals(productsPage.getPageTitle(), "Products", "Не вернулись на страницу товаров");
        assertTrue(productsPage.isProductListDisplayed(), "Список товаров не отображается");

        logStep("✅ Успешное возвращение к покупкам");
    }
}