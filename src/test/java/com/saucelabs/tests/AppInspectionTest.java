package com.saucelabs.tests;

import com.saucelabs.base.BaseTest;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class AppInspectionTest extends BaseTest {

    @Test
    public void inspectCurrentAppState() {
        try {
            // Wait for app to load
            System.out.println("App is launching... waiting 10 seconds");
            Thread.sleep(10000);
            
            // Take a screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("app_screenshot.png"));
            System.out.println("Screenshot saved as app_screenshot.png");
            
            // Get page source
            String pageSource = driver.getPageSource();
            System.out.println("=== PAGE SOURCE ===");
            System.out.println(pageSource);
            
            // Try to find common login elements
            System.out.println("\n=== SEARCHING FOR LOGIN ELEMENTS ===");
            
            // Search for elements containing "username" or "login"
            List<WebElement> usernameElements = driver.findElements(org.openqa.selenium.By.xpath("//*[contains(@content-desc, 'sername') or contains(@text, 'sername') or contains(@resource-id, 'sername')]"));
            System.out.println("Found " + usernameElements.size() + " username-related elements");
            
            List<WebElement> loginElements = driver.findElements(org.openqa.selenium.By.xpath("//*[contains(@content-desc, 'ogin') or contains(@text, 'ogin') or contains(@resource-id, 'ogin')]"));
            System.out.println("Found " + loginElements.size() + " login-related elements");
            
            // List all clickable elements
            List<WebElement> clickableElements = driver.findElements(org.openqa.selenium.By.xpath("//*[@clickable='true']"));
            System.out.println("\n=== CLICKABLE ELEMENTS ===");
            System.out.println("Found " + clickableElements.size() + " clickable elements:");
            
            for (int i = 0; i < Math.min(clickableElements.size(), 10); i++) {
                WebElement element = clickableElements.get(i);
                String text = element.getText();
                String contentDesc = element.getAttribute("content-desc");
                String resourceId = element.getAttribute("resource-id");
                System.out.println("Element " + (i+1) + ":");
                System.out.println("  Text: " + text);
                System.out.println("  Content-desc: " + contentDesc);
                System.out.println("  Resource-id: " + resourceId);
                System.out.println("  ---");
            }
            
            // Keep the app open for manual inspection
            System.out.println("\n=== APP IS NOW OPEN FOR INSPECTION ===");
            System.out.println("Check your Android emulator window to see the app!");
            Thread.sleep(30000); // Keep app open for 30 seconds
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
