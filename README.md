# Appium Mobile Test Automation Framework

A cross-platform mobile test automation framework using Java, Appium, Maven, and TestNG with Page Object Model pattern for testing the Sauce Labs "My Demo App" on both Android and iOS platforms.

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/
│           └── saucelabs/
│               ├── base/
│               │   └── BaseTest.java          # Base test class with driver setup
│               └── pages/
│                   ├── LoginPage.java         # Login page object
│                   ├── ProductsPage.java      # Products page object
│                   ├── CartPage.java          # Cart page object
│                   ├── CheckoutPage.java      # Checkout page object
│                   └── SideMenuPage.java      # Side menu page object
└── test/
    └── java/
        └── com/
            └── saucelabs/
                └── tests/
                    └── DemoAppTests.java      # Test classes
pom.xml                                        # Maven configuration
testng.xml                                     # TestNG suite configuration
README.md                                      # This file
```

## Features

- **Cross-Platform Support**: Run tests on both Android and iOS by changing a parameter
- **Page Object Model**: Clean separation of page logic and test logic
- **Dual Locators**: Each element uses both Android and iOS locators for cross-platform compatibility
- **TestNG Integration**: Parallel test execution with comprehensive reporting
- **Maven Build**: Easy dependency management and build automation

## Prerequisites

1. **Java Development Kit (JDK) 11 or higher**
2. **Maven 3.6+**
3. **Appium Server 2.0+**
4. **Android Studio** (for Android testing)
5. **Xcode** (for iOS testing on macOS)
6. **Mobile Apps**:
   - Android: Sauce Labs My Demo App (APK)
   - iOS: Sauce Labs My Demo App (.app file)

## Setup Instructions

### 1. Install Appium

```bash
# Install Appium globally
npm install -g appium

# Install drivers
appium driver install uiautomator2    # For Android
appium driver install xcuitest        # For iOS
```

### 2. Configure Devices

#### Android Setup:
- Start an Android emulator or connect a physical device
- Enable Developer Options and USB Debugging
- Install the Sauce Labs My Demo App APK

#### iOS Setup (macOS only):
- Start an iOS Simulator
- Download and unzip the iOS app
- Update the app path in `BaseTest.java` (line 29)

### 3. Start Appium Server

```bash
appium server
```

## Running Tests

### Run All Tests for Android Only:
```bash
mvn clean test -Dtest=DemoAppTests -DplatformName=android
```

### Run All Tests for iOS Only:
```bash
mvn clean test -Dtest=DemoAppTests -DplatformName=ios
```

### Run Cross-Platform Tests:
```bash
mvn clean test
```

### Run Specific Test Methods:
```bash
mvn clean test -Dtest=DemoAppTests#testSuccessfulLogin -DplatformName=android
```

## Test Cases Included

| Test ID | Description | Test Method |
|---------|-------------|-------------|
| TC-LOGIN-001 | Successful Login with Standard User | `testSuccessfulLogin()` |
| TC-LOGIN-002 | Failed Login with Invalid Password | `testFailedLogin()` |
| TC-LOGIN-003 | Failed Login with Locked Out User | `testLockedOutUserLogin()` |
| TC-PROD-002 | Verify Product Sorting by Price | `testProductSortByPrice()` |
| TC-E2E-001 | Complete Purchase Order Flow | `testEndToEndCheckout()` |
| TC-NAV-001 | Verify User Logout | `testUserLogout()` |

## Framework Architecture

### BaseTest Class
- Handles driver initialization for both Android and iOS
- Uses `@Parameters` annotation to accept platform type
- Manages implicit waits and driver cleanup

### Page Object Model
- Each page has its own class with locators and methods
- Uses `@AndroidFindBy` and `@iOSXCUITFindBy` annotations
- Initializes elements using `AppiumFieldDecorator`

### Test Configuration
- `testng.xml` defines parallel execution for both platforms
- Maven Surefire plugin manages test execution
- Tests can run sequentially or in parallel

## Configuration

### Updating Device Configuration

Edit `BaseTest.java` to modify device settings:

```java
// Android configuration
.setDeviceName("Your Android Device Name")
.setPlatformVersion("Android Version")

// iOS configuration  
.setDeviceName("iPhone 15")
.setPlatformVersion("17.0")
.setApp("/path/to/your/MyRNDemoApp.app")
```

### Test Data

The framework uses the following test credentials:
- **Valid User**: `standard_user` / `secret_sauce`
- **Locked User**: `locked_out_user` / `secret_sauce`

## Troubleshooting

### Common Issues:

1. **App not found**: Ensure the app package/bundle ID is correct
2. **Element not found**: Check if locators are correct for your app version
3. **Driver timeout**: Increase implicit wait time in BaseTest
4. **iOS app path**: Use absolute path to the .app file

### Logs and Debugging:

- Appium server logs: Check the Appium server console
- Test logs: Available in `target/surefire-reports/`
- Screenshots: Can be added to failed tests

## Extending the Framework

### Adding New Page Objects:
1. Create a new class in `com.saucelabs.pages`
2. Add dual locators using `@AndroidFindBy` and `@iOSXCUITFindBy`
3. Initialize elements in constructor with `AppiumFieldDecorator`

### Adding New Tests:
1. Add test methods to `DemoAppTests.java` or create new test classes
2. Update `testng.xml` if creating new test classes
3. Follow the naming convention: `test[FeatureName]`

## Dependencies

- **Appium Java Client**: 9.2.2
- **TestNG**: 7.10.2
- **Selenium WebDriver**: 4.22.0
- **Maven Surefire Plugin**: 3.3.0

## Contributing

1. Follow the existing code structure and naming conventions
2. Add dual locators for all new elements
3. Include comprehensive test documentation
4. Update this README for any significant changes

## License

This project is for educational and testing purposes.

## ✅ Framework Status: Production Ready

Successfully tested and verified on:
- **Android Emulator**: API 36 (Android 14)
- **Sauce Labs My Demo App**: v1.3.0
- **Java**: 11+ with Maven 3.9.10
- **Appium**: 2.18.0 with UiAutomator2 driver
- **TestNG**: 7.10.2 with cross-platform execution
- **Date Verified**: August 10, 2025

All test scenarios pass successfully with visible UI automation on Android emulator.
