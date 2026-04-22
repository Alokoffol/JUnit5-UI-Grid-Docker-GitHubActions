package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    private final By cartItem = By.className("cart_item");
    private final By removeButton = By.cssSelector("[data-test^='remove']");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By checkoutButton = By.id("checkout");
    private final By cartList = By.className("cart_list");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartList)).isDisplayed();
    }

    public int getItemCount() {
        List<WebElement> items = driver.findElements(cartItem);
        return items.size();
    }

    public void clickCheckout() {
        clickElement(checkoutButton);
    }

    public void removeFirstItem() {
        List<WebElement> removeButtons = driver.findElements(removeButton);
        if (!removeButtons.isEmpty()) {
            removeButtons.get(0).click();
            waitForElementToDisappear(cartItem);
        }
    }

    public boolean isCartEmpty() {
        List<WebElement> items = driver.findElements(cartItem);
        return items.isEmpty();
    }

    public void clickContinueShopping() {
        clickElement(continueShoppingButton);
    }

    private void waitForElementToDisappear(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}