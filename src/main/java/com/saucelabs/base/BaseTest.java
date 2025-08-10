package com.saucelabs.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {
    protected AppiumDriver driver;

    @Parameters({"platform"})
    @BeforeMethod
    public void setUp(String platform) throws MalformedURLException {
        if (platform.equalsIgnoreCase("android")) {
            UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("Android Emulator")
                .setApp("/Users/vuong/Documents/Automation Learning/AppiumDemoProject/apps/Android-MyDemoAppRN.1.3.0.build-244.apk")
                .setAppPackage("com.saucelabs.mydemoapp.rn")
                .setAppActivity("com.saucelabs.mydemoapp.rn.MainActivity")
                .setAutomationName("UiAutomator2");
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        } else if (platform.equalsIgnoreCase("ios")) {
            XCUITestOptions options = new XCUITestOptions()
                .setPlatformName("iOS")
                .setPlatformVersion("18.6")
                .setDeviceName("iPhone 16 Pro")
                .setBundleId("com.saucelabs.mydemoapp.rn")
                .setAutomationName("XCUITest");
            driver = new IOSDriver(new URL("http://127.0.0.1:4723"), options);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
