package com.saucelabs.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage {
    private AppiumDriver driver;
    
    @AndroidFindBy(accessibility = "test-Modal Selector Button")
    @iOSXCUITFindBy(accessibility = "test-Modal Selector Button")
    private WebElement sortIcon;

    @AndroidFindBy(accessibility = "Name (A to Z)")
    @iOSXCUITFindBy(accessibility = "Name (A to Z)")
    private WebElement sortByNameAZ;

    @AndroidFindBy(accessibility = "Price (low to high)")
    @iOSXCUITFindBy(accessibility = "Price (low to high)")
    private WebElement sortByPriceLowHigh;

    @AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"test-ADD TO CART\"])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeOther[@name=\"test-ADD TO CART\"])[1]")
    private WebElement addBackpackToCartButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Sauce Labs Backpack']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Sauce Labs Backpack']")
    private WebElement backpackTitle;

    @AndroidFindBy(accessibility = "test-Cart")
    @iOSXCUITFindBy(accessibility = "test-Cart")
    private WebElement cartIcon;

    @AndroidFindBy(accessibility = "test-Menu")
    @iOSXCUITFindBy(accessibility = "test-Menu")
    private WebElement menuIcon;
    
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Sauce Labs Onesie']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Sauce Labs Onesie']")
    private WebElement onesieTitle;

    public ProductsPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean isSortIconDisplayed() {
        return sortIcon.isDisplayed();
    }

    public void sortProductsBy(String sortType) {
        sortIcon.click();
        if (sortType.equals("Name (A to Z)")) {
            sortByNameAZ.click();
        } else if (sortType.equals("Price (low to high)")) {
            sortByPriceLowHigh.click();
        }
    }

    public String getFirstProductName() {
        // After sorting by price, the first product should be Sauce Labs Onesie
        return onesieTitle.getText();
    }

    public void addBackpackToCart() {
        addBackpackToCartButton.click();
        cartIcon.click();
    }

    public void openMenu() {
        menuIcon.click();
    }
}
