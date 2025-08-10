package com.saucelabs.tests;

import com.saucelabs.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class CheckoutInspectionTest extends BaseTest {

    @Test(description = "Inspect checkout flow elements for both Android and iOS")
    public void inspectCheckoutElements() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            System.out.println("🔍 === CHECKOUT ELEMENT INSPECTION === 🔍");
            System.out.println("Platform: " + (driver.toString().contains("Android") ? "ANDROID" : "iOS"));
            
            // Wait for app to load
            Thread.sleep(5000);
            
            System.out.println("\n📱 === CURRENT SCREEN ANALYSIS ===");
            
            // Check what screen we're on
            String pageSource = driver.getPageSource();
            System.out.println("Screen contains:");
            if (pageSource.contains("Products") || pageSource.contains("Catalog")) {
                System.out.println("✅ Products/Catalog screen detected");
                inspectProductsScreen();
            } else if (pageSource.contains("Username") || pageSource.contains("Password")) {
                System.out.println("✅ Login screen detected");
                inspectLoginScreen();
            } else if (pageSource.contains("Cart") || pageSource.contains("Shopping")) {
                System.out.println("✅ Cart screen detected");
                inspectCartScreen();
            } else if (pageSource.contains("Checkout") || pageSource.contains("Payment")) {
                System.out.println("✅ Checkout screen detected");
                inspectCheckoutScreen();
            }
            
            // Try to navigate through checkout flow
            System.out.println("\n🛒 === ATTEMPTING CHECKOUT NAVIGATION ===");
            navigateToCheckout();
            
        } catch (Exception e) {
            System.out.println("❌ Inspection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void inspectProductsScreen() {
        System.out.println("\n🏪 === PRODUCTS SCREEN ELEMENTS ===");
        
        // Look for product items
        findAndLogElements("Product items", "store item", "Sauce Labs");
        
        // Look for add to cart buttons
        findAndLogElements("Add to cart", "ADD TO CART", "Add to cart");
        
        // Look for cart icon
        findAndLogElements("Cart icon", "cart", "shopping");
        
        // Look for menu
        findAndLogElements("Menu", "menu", "Menu");
    }
    
    private void inspectLoginScreen() {
        System.out.println("\n🔐 === LOGIN SCREEN ELEMENTS ===");
        
        // Look for username field
        findAndLogElements("Username field", "Username", "test-Username");
        
        // Look for password field
        findAndLogElements("Password field", "Password", "test-Password");
        
        // Look for login button
        findAndLogElements("Login button", "LOGIN", "test-LOGIN");
    }
    
    private void inspectCartScreen() {
        System.out.println("\n🛒 === CART SCREEN ELEMENTS ===");
        
        // Look for checkout button
        findAndLogElements("Checkout button", "CHECKOUT", "checkout");
        
        // Look for cart items
        findAndLogElements("Cart items", "cart item", "item");
        
        // Look for remove buttons
        findAndLogElements("Remove buttons", "REMOVE", "remove");
    }
    
    private void inspectCheckoutScreen() {
        System.out.println("\n💳 === CHECKOUT SCREEN ELEMENTS ===");
        
        // Look for form fields
        findAndLogElements("Name fields", "Full Name", "Given Name", "Family Name");
        findAndLogElements("Address fields", "Address", "address");
        findAndLogElements("City fields", "City", "city");
        findAndLogElements("Zip fields", "Zip", "Postal", "zip");
        findAndLogElements("Country fields", "Country", "country");
        
        // Look for payment fields
        findAndLogElements("Card fields", "Card", "credit", "payment");
        findAndLogElements("CVV fields", "CVV", "Security", "cvc");
        findAndLogElements("Expiry fields", "Expiry", "expir", "MM/YY");
        
        // Look for submit buttons
        findAndLogElements("Submit buttons", "PLACE ORDER", "PAY", "COMPLETE", "FINISH");
    }
    
    private void findAndLogElements(String description, String... searchTerms) {
        System.out.println("\n🔍 Searching for: " + description);
        
        for (String term : searchTerms) {
            try {
                // Search by accessibility ID
                List<WebElement> elements = driver.findElements(By.name(term));
                if (!elements.isEmpty()) {
                    System.out.println("  ✅ Found by name: '" + term + "' (" + elements.size() + " elements)");
                    logElementDetails(elements.get(0));
                    continue;
                }
                
                // Search by text content
                elements = driver.findElements(By.xpath("//*[contains(@text, '" + term + "') or contains(@label, '" + term + "') or contains(@name, '" + term + "')]"));
                if (!elements.isEmpty()) {
                    System.out.println("  ✅ Found by content: '" + term + "' (" + elements.size() + " elements)");
                    logElementDetails(elements.get(0));
                    continue;
                }
                
                // Search by partial match
                elements = driver.findElements(By.xpath("//*[contains(translate(@text, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + term.toLowerCase() + "')]"));
                if (!elements.isEmpty()) {
                    System.out.println("  ✅ Found by partial match: '" + term + "' (" + elements.size() + " elements)");
                    logElementDetails(elements.get(0));
                }
                
            } catch (Exception e) {
                // Continue searching
            }
        }
    }
    
    private void logElementDetails(WebElement element) {
        try {
            String text = element.getText();
            String name = element.getAttribute("name");
            String contentDesc = element.getAttribute("content-desc");
            String resourceId = element.getAttribute("resource-id");
            String className = element.getAttribute("class");
            String accessibilityId = element.getAttribute("accessibility-id");
            
            System.out.println("    📍 Element details:");
            if (text != null && !text.isEmpty()) System.out.println("      Text: '" + text + "'");
            if (name != null && !name.isEmpty()) System.out.println("      Name: '" + name + "'");
            if (contentDesc != null && !contentDesc.isEmpty()) System.out.println("      Content-desc: '" + contentDesc + "'");
            if (resourceId != null && !resourceId.isEmpty()) System.out.println("      Resource-id: '" + resourceId + "'");
            if (className != null && !className.isEmpty()) System.out.println("      Class: '" + className + "'");
            if (accessibilityId != null && !accessibilityId.isEmpty()) System.out.println("      Accessibility-id: '" + accessibilityId + "'");
            
            // Suggest locator strategies
            System.out.println("    💡 Suggested locators:");
            if (name != null && !name.isEmpty()) {
                System.out.println("      By.name(\"" + name + "\")");
                System.out.println("      @AndroidFindBy(accessibility = \"" + name + "\")");
                System.out.println("      @iOSXCUITFindBy(accessibility = \"" + name + "\")");
            }
            if (contentDesc != null && !contentDesc.isEmpty()) {
                System.out.println("      By.accessibilityId(\"" + contentDesc + "\")");
            }
            if (resourceId != null && !resourceId.isEmpty()) {
                System.out.println("      By.id(\"" + resourceId + "\")");
            }
            
        } catch (Exception e) {
            System.out.println("    ⚠️ Could not get element details: " + e.getMessage());
        }
    }
    
    private void navigateToCheckout() {
        try {
            System.out.println("\n🚀 === ATTEMPTING CHECKOUT FLOW NAVIGATION ===");
            
            // Step 1: Navigate to login if needed (iOS specific)
            if (driver.toString().contains("ios")) {
                try {
                    WebElement menuTab = driver.findElement(By.name("tab bar option menu"));
                    menuTab.click();
                    Thread.sleep(2000);
                    
                    WebElement loginItem = driver.findElement(By.name("menu item log in"));
                    loginItem.click();
                    Thread.sleep(2000);
                    System.out.println("✅ Navigated to login screen (iOS)");
                } catch (Exception e) {
                    System.out.println("⚠️ Could not navigate to login: " + e.getMessage());
                }
            }
            
            // Step 2: Try to login
            try {
                WebElement usernameField = driver.findElement(By.name("test-Username"));
                WebElement passwordField = driver.findElement(By.name("test-Password"));
                WebElement loginButton = driver.findElement(By.name("test-LOGIN"));
                
                usernameField.sendKeys("standard_user");
                passwordField.sendKeys("secret_sauce");
                loginButton.click();
                Thread.sleep(3000);
                System.out.println("✅ Login successful");
            } catch (Exception e) {
                System.out.println("⚠️ Login step failed: " + e.getMessage());
            }
            
            // Step 3: Add product to cart
            try {
                WebElement addToCartButton = driver.findElement(By.xpath("//*[contains(@name, 'ADD TO CART') or contains(@text, 'ADD TO CART')]"));
                addToCartButton.click();
                Thread.sleep(2000);
                System.out.println("✅ Added product to cart");
            } catch (Exception e) {
                System.out.println("⚠️ Add to cart failed: " + e.getMessage());
            }
            
            // Step 4: Go to cart
            try {
                WebElement cartIcon = driver.findElement(By.xpath("//*[contains(@name, 'cart') or contains(@content-desc, 'cart')]"));
                cartIcon.click();
                Thread.sleep(2000);
                System.out.println("✅ Navigated to cart");
                
                // Inspect cart screen
                inspectCartScreen();
            } catch (Exception e) {
                System.out.println("⚠️ Cart navigation failed: " + e.getMessage());
            }
            
            // Step 5: Proceed to checkout
            try {
                WebElement checkoutButton = driver.findElement(By.xpath("//*[contains(@name, 'CHECKOUT') or contains(@text, 'CHECKOUT')]"));
                checkoutButton.click();
                Thread.sleep(3000);
                System.out.println("✅ Proceeded to checkout");
                
                // Inspect checkout screen
                inspectCheckoutScreen();
            } catch (Exception e) {
                System.out.println("⚠️ Checkout navigation failed: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("❌ Navigation failed: " + e.getMessage());
        }
    }
}
