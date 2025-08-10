package com.saucelabs.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
    private AppiumDriver driver;
    
    @AndroidFindBy(accessibility = "test-REMOVE")
    @iOSXCUITFindBy(accessibility = "test-REMOVE")
    private WebElement removeButton;

    @AndroidFindBy(accessibility = "test-CHECKOUT")
    @iOSXCUITFindBy(accessibility = "test-CHECKOUT")
    private WebElement checkoutButton;

    public CartPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void removeItem() {
        removeButton.click();
    }

    public void proceedToCheckout() {
        checkoutButton.click();
    }
}
