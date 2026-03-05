package view;

import io.appium.java_client.AppiumDriver;
import resources.config.AndroidConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductsView {

    private static final String APP_PACKAGE = "com.saucelabs.mydemoapp.android";
    private AppiumDriver driver;
    private final WebDriverWait wait;

    public ProductsView(AppiumDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    By addToCartBtn = By.id(APP_PACKAGE + ":id/cartBt");
    By addToCartByText = By.xpath("//*[@text='Add To Cart']");
    By addToCartByDesc = By.xpath("//*[@content-desc='Tap to add product to cart']");

    By productImage = By.id(APP_PACKAGE + ":id/productIV");
    By usernameInput = By.id(APP_PACKAGE + ":id/nameET");
    By passwordInput = By.id(APP_PACKAGE + ":id/passwordET");
    By loginButton = By.id(APP_PACKAGE + ":id/loginBtn");
    By cartIcon = By.id(APP_PACKAGE + ":id/cartIV");

    public void clickAddToCart(){

        loginIfNeeded();

        if (clickFirstVisible(List.of(addToCartBtn, addToCartByText, addToCartByDesc))) {
            return;
        }

        openFirstProductIfNeeded();
        if (clickFirstVisible(List.of(addToCartBtn, addToCartByText, addToCartByDesc))) {
            return;
        }

        String source = driver.getPageSource();
        String snippet = source.length() > 1800 ? source.substring(0, 1800) : source;
        throw new TimeoutException("No se encontro boton Add To Cart con los localizadores configurados. Screen snippet: " + snippet);

    }

    private void loginIfNeeded() {
        List<WebElement> userInputs = driver.findElements(usernameInput);
        List<WebElement> passInputs = driver.findElements(passwordInput);
        List<WebElement> loginButtons = driver.findElements(loginButton);

        if (userInputs.isEmpty() || passInputs.isEmpty() || loginButtons.isEmpty()) {
            return;
        }

        WebElement user = wait.until(ExpectedConditions.elementToBeClickable(userInputs.get(0)));
        WebElement pass = wait.until(ExpectedConditions.elementToBeClickable(passInputs.get(0)));
        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(loginButtons.get(0)));

        String username = AndroidConfig.getRequired("user.email");
        String password = AndroidConfig.getRequired("user.password");

        user.clear();
        user.sendKeys(username);
        pass.clear();
        pass.sendKeys(password);
        login.click();
    }

    private void openFirstProductIfNeeded() {
        List<WebElement> products = driver.findElements(productImage);
        if (products.isEmpty()) {
            return;
        }

        wait.until(ExpectedConditions.elementToBeClickable(products.get(0))).click();
    }

    private boolean clickFirstVisible(List<By> selectors) {
        for (By selector : selectors) {
            List<WebElement> elements = driver.findElements(selector);
            if (!elements.isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(elements.get(0))).click();
                return true;
            }
        }

        return false;
    }

    public void openCart(){

        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();

    }

}