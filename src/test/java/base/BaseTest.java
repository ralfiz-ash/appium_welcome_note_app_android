package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

public class BaseTest {

    protected AppiumDriver driver;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        System.out.println("DEBUG: setUp() called");
        String userDir = System.getProperty("user.dir");
        System.out.println("DEBUG: user.dir = " + userDir);

        // Check for APK in multiple locations
        File app = new File(userDir, "app/build/outputs/apk/debug/app-debug.apk");
        if (!app.exists()) {
            app = new File(userDir, "src/apk/app-debug.apk");
        }
        if (!app.exists()) {
            app = new File(userDir, "app-debug.apk");
        }

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("emulator-5554")
                .setAppPackage("com.example.welcomenote")
                .setAppActivity(".MainActivity")
                .setNoReset(false)
                .setNewCommandTimeout(Duration.ofSeconds(300));

        // Only set app path if APK file exists, otherwise assume app is pre-installed
        if (app.exists()) {
            System.out.println("DEBUG: Found APK at " + app.getAbsolutePath() + ". Will install if needed.");
            options.setApp(app.getAbsolutePath());
        } else {
            System.out.println(
                    "DEBUG: APK file not found. Will attempt to launch pre-installed app: com.example.welcomenote");
        }

        try {
            driver = new AndroidDriver(
                    URI.create("http://127.0.0.1:4723").toURL(),
                    options);
            System.out.println("DEBUG: Driver initialized successfully");
        } catch (MalformedURLException e) {
            System.err.println("FATAL ERROR: Invalid Appium server URL!");
            throw new RuntimeException("Invalid Appium URL", e);
        } catch (Exception e) {
            System.err.println("ERROR: Failed to initialize driver: " + e.getMessage());
            e.printStackTrace();
            throw e; // rethrow because test cannot continue without driver
        }

    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void logInfo(String message) {
        Reporter.log(message, true);
        System.out.println(message);
    }
}
