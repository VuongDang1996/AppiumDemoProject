package com.saucelabs.tests;

import com.saucelabs.base.BaseTest;
import com.saucelabs.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class IOSAppTests extends BaseTest {

    private void navigateToLoginIfNeeded() {
        try {
            // Check if we're already on the login screen
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.name("test-Username")));
        } catch (Exception e) {
            // We're not on login screen, need to navigate via menu
            try {
                // Tap on Menu tab
                WebElement menuTab = driver.findElement(By.name("tab bar option menu"));
                menuTab.click();
                Thread.sleep(2000);
                
                // Tap on Login menu item
                WebElement loginMenuItem = driver.findElement(By.name("menu item log in"));
                loginMenuItem.click();
                Thread.sleep(2000);
            } catch (Exception navException) {
                System.out.println("Could not navigate to login screen: " + navException.getMessage());
            }
        }
    }

    @Test(description = "iOS: Successful Login with Standard User")
    public void testSuccessfulLogin() {
        navigateToLoginIfNeeded();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isSortIconDisplayed(), "Products page is not displayed.");
    }

    @Test(description = "iOS: Failed Login with Invalid Password")
    public void testFailedLogin() {
        navigateToLoginIfNeeded();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "wrong_password");
        Assert.assertEquals(loginPage.getErrorMessageText(), "Username and password do not match any user in this service.");
    }
    
    @Test(description = "iOS: Failed Login with Locked Out User")
    public void testLockedOutUserLogin() {
        navigateToLoginIfNeeded();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");
        Assert.assertEquals(loginPage.getErrorMessageText(), "Sorry, this user has been locked out.");
    }

    @Test(description = "iOS: Verify Product Sorting by Price (Low to High)")
    public void testProductSortByPrice() {
        navigateToLoginIfNeeded();
        new LoginPage(driver).login("standard_user", "secret_sauce");
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.sortProductsBy("Price (low to high)");
        Assert.assertEquals(productsPage.getFirstProductName(), "Sauce Labs Onesie");
    }

    @Test(description = "iOS: Complete a Full Purchase Order")
    public void testEndToEndCheckout() {
        navigateToLoginIfNeeded();
        new LoginPage(driver).login("standard_user", "secret_sauce");
        new ProductsPage(driver).addBackpackToCart();
        new CartPage(driver).proceedToCheckout();
        
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterShippingDetails("John Doe", "123 Main St", "Anytown", "12345");
        checkoutPage.continueToPayment();
        checkoutPage.enterPaymentDetails("John Doe", "1234567812345678", "12/25", "123");
        checkoutPage.placeOrder();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='Thank you for your order']"))).isDisplayed());
    }
    
    @Test(description = "iOS: Verify User Logout")
    public void testUserLogout() {
        navigateToLoginIfNeeded();
        new LoginPage(driver).login("standard_user", "secret_sauce");
        new ProductsPage(driver).openMenu();
        new SideMenuPage(driver).logout();
        
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "User was not returned to the login screen.");
    }
}
