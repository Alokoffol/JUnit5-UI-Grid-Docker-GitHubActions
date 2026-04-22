package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductsPage extends BasePage {

    private final By pageTitle = By.className("title");
    private final By addToCartButton = By.cssSelector("[data-test^='add-to-cart']");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By shoppingCartLink = By.className("shopping_cart_link");
    private final By productList = By.className("inventory_item");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return waitForElement(pageTitle).getText();
    }

    public void addFirstProductToCart() {
        List<WebElement> addButtons = driver.findElements(addToCartButton);
        if (!addButtons.isEmpty()) {
            addButtons.get(0).click();
        }
    }

    public int getCartItemCount() {
        try {
            String text = waitForElement(cartBadge).getText();
            return Integer.parseInt(text);
        } catch (Exception e) {
            return 0;
        }
    }

    public void goToCart() {
        clickElement(shoppingCartLink);
    }

    public boolean isProductListDisplayed() {
        List<WebElement> products = driver.findElements(productList);
        return !products.isEmpty() && products.get(0).isDisplayed();
    }
}