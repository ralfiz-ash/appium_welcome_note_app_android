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

        // Check for APK in standard build output first
        File app = new File(userDir, "app/build/outputs/apk/debug/app-debug.apk");
        if (!app.exists()) {
            // Check in src/apk/ (common manual storage)
            app = new File(userDir, "src/apk/app-debug.apk");
        }
        if (!app.exists()) {
            // Fallback to root directory
            app = new File(userDir, "app-debug.apk");
        }
        System.out.println("DEBUG: App path = " + app.getAbsolutePath());

        if (!app.exists()) {
            throw new RuntimeException(
                    "APK file missing! Please ensure app-debug.apk is in 'app/build/outputs/apk/debug/', 'src/apk/', or project root.");
        }

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("emulator-5554")
                .setApp(app.getAbsolutePath())
                .setAppPackage("com.example.appiumpoc")
                .setAppActivity(".MainActivity")
                .setNoReset(false)
                .setNewCommandTimeout(Duration.ofSeconds(300));

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
