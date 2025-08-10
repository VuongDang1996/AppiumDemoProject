package com.saucelabs.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {
    private AppiumDriver driver;
    
    @AndroidFindBy(accessibility = "test-Full Name")
    @iOSXCUITFindBy(accessibility = "test-Full Name")
    private WebElement fullNameField;

    @AndroidFindBy(accessibility = "test-Address Line 1")
    @iOSXCUITFindBy(accessibility = "test-Address Line 1")
    private WebElement addressLine1Field;

    @AndroidFindBy(accessibility = "test-Address Line 2")
    @iOSXCUITFindBy(accessibility = "test-Address Line 2")
    private WebElement addressLine2Field;

    @AndroidFindBy(accessibility = "test-City")
    @iOSXCUITFindBy(accessibility = "test-City")
    private WebElement cityField;

    @AndroidFindBy(accessibility = "test-State/Region")
    @iOSXCUITFindBy(accessibility = "test-State/Region")
    private WebElement stateRegionField;

    @AndroidFindBy(accessibility = "test-Zip Code")
    @iOSXCUITFindBy(accessibility = "test-Zip Code")
    private WebElement zipCodeField;

    @AndroidFindBy(accessibility = "test-Country")
    @iOSXCUITFindBy(accessibility = "test-Country")
    private WebElement countryField;

    @AndroidFindBy(accessibility = "test-To Payment")
    @iOSXCUITFindBy(accessibility = "test-To Payment")
    private WebElement toPaymentButton;

    @AndroidFindBy(accessibility = "test-Full Name")
    @iOSXCUITFindBy(accessibility = "test-Full Name")
    private WebElement paymentFullNameField;

    @AndroidFindBy(accessibility = "test-Card Number")
    @iOSXCUITFindBy(accessibility = "test-Card Number")
    private WebElement cardNumberField;

    @AndroidFindBy(accessibility = "test-Expiration Date")
    @iOSXCUITFindBy(accessibility = "test-Expiration Date")
    private WebElement expirationDateField;

    @AndroidFindBy(accessibility = "test-Security Code")
    @iOSXCUITFindBy(accessibility = "test-Security Code")
    private WebElement securityCodeField;

    @AndroidFindBy(accessibility = "test-PLACE ORDER")
    @iOSXCUITFindBy(accessibility = "test-PLACE ORDER")
    private WebElement placeOrderButton;

    @AndroidFindBy(xpath = "//*[@text='Thank you for your order']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Thank you for your order']")
    private WebElement confirmationMessage;

    public CheckoutPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void enterShippingDetails(String fullName, String address, String city, String zipCode) {
        fullNameField.clear();
        fullNameField.sendKeys(fullName);
        addressLine1Field.clear();
        addressLine1Field.sendKeys(address);
        cityField.clear();
        cityField.sendKeys(city);
        zipCodeField.clear();
        zipCodeField.sendKeys(zipCode);
    }

    public void continueToPayment() {
        toPaymentButton.click();
    }

    public void enterPaymentDetails(String fullName, String cardNumber, String expirationDate, String securityCode) {
        paymentFullNameField.clear();
        paymentFullNameField.sendKeys(fullName);
        cardNumberField.clear();
        cardNumberField.sendKeys(cardNumber);
        expirationDateField.clear();
        expirationDateField.sendKeys(expirationDate);
        securityCodeField.clear();
        securityCodeField.sendKeys(securityCode);
    }

    public void placeOrder() {
        placeOrderButton.click();
    }

    public boolean isConfirmationDisplayed() {
        return confirmationMessage.isDisplayed();
    }
}
