package com.saucelabs.tests;

import com.saucelabs.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IOSBasicTest extends BaseTest {

    @Test(description = "iOS: Verify app launches and shows products")
    public void testAppLaunch() {
        try {
            // Check if we can see the Products title
            WebElement productsTitle = driver.findElement(By.name("Products"));
            Assert.assertTrue(productsTitle.isDisplayed(), "Products title should be visible");
            
            // Check if we can see the sort button
            WebElement sortButton = driver.findElement(By.name("sort button"));
            Assert.assertTrue(sortButton.isDisplayed(), "Sort button should be visible");
            
            // Check if we can see store items
            WebElement firstProduct = driver.findElement(By.name("Sauce Labs Backpack"));
            Assert.assertTrue(firstProduct.isDisplayed(), "First product should be visible");
            
            System.out.println("✅ iOS app launched successfully and shows products!");
            
        } catch (Exception e) {
            System.out.println("❌ Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(description = "iOS: Test navigation to menu")
    public void testMenuNavigation() {
        try {
            // Tap on Menu tab
            WebElement menuTab = driver.findElement(By.name("tab bar option menu"));
            Assert.assertTrue(menuTab.isDisplayed(), "Menu tab should be visible");
            menuTab.click();
            
            Thread.sleep(3000); // Wait for animation
            
            // Check if we can see menu items
            WebElement loginMenuItem = driver.findElement(By.name("menu item log in"));
            Assert.assertTrue(loginMenuItem.isDisplayed(), "Login menu item should be visible");
            
            System.out.println("✅ iOS menu navigation works!");
            
        } catch (Exception e) {
            System.out.println("❌ Menu navigation failed: " + e.getMessage());
            Assert.fail("Menu navigation failed: " + e.getMessage());
        }
    }
}
