package com.saucelabs.tests;

import com.saucelabs.base.BaseTest;
import com.saucelabs.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class DemoAppTests extends BaseTest {

    @Test(description = "TC-LOGIN-001: Successful Login with Standard User")
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isSortIconDisplayed(), "Products page is not displayed.");
    }

    @Test(description = "TC-LOGIN-002: Failed Login with Invalid Password")
    public void testFailedLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "wrong_password");
        Assert.assertEquals(loginPage.getErrorMessageText(), "Username and password do not match any user in this service.");
    }
    
    @Test(description = "TC-LOGIN-003: Failed Login with Locked Out User")
    public void testLockedOutUserLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");
        Assert.assertEquals(loginPage.getErrorMessageText(), "Sorry, this user has been locked out.");
    }

    @Test(description = "TC-PROD-002: Verify Product Sorting by Price (Low to High)")
    public void testProductSortByPrice() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.sortProductsBy("Price (low to high)");
        Assert.assertEquals(productsPage.getFirstProductName(), "Sauce Labs Onesie");
    }

    @Test(description = "TC-E2E-001: Complete a Full Purchase Order")
    public void testEndToEndCheckout() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        new ProductsPage(driver).addBackpackToCart();
        new CartPage(driver).proceedToCheckout();
        
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterShippingDetails("John Doe", "123 Main St", "Anytown", "12345");
        checkoutPage.continueToPayment();
        checkoutPage.enterPaymentDetails("John Doe", "1234567812345678", "12/25", "123");
        checkoutPage.placeOrder();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Thank you for your order']"))).isDisplayed());
    }
    
    @Test(description = "TC-NAV-001: Verify User Logout")
    public void testUserLogout() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        new ProductsPage(driver).openMenu();
        new SideMenuPage(driver).logout();
        
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "User was not returned to the login screen.");
    }
}
