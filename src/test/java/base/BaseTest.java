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
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    protected AppiumDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {

        File app = new File("app-debug.apk");
        if (!app.exists()) {
            throw new RuntimeException("APK NOT FOUND: " + app.getAbsolutePath());
        }

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("Android Emulator")   // Required for emulator
                .setApp(app.getAbsolutePath())
                .setAppPackage("com.example.welcomenote")
                .setAppActivity(".MainActivity")
                .setNoReset(false)
                .setNewCommandTimeout(Duration.ofSeconds(300));

        driver = new AndroidDriver(
                URI.create("http://127.0.0.1:4723").toURL(),
                options
        );
    }

    /*public void setUp() throws MalformedURLException {
        System.out.println("DEBUG: setUp() called");
        String userDir = System.getProperty("user.dir");
        System.out.println("DEBUG: user.dir = " + userDir);

        File app = new File(userDir, "app-debug.apk");
        System.out.println("DEBUG: App path = " + app.getAbsolutePath());

        if (!app.exists()) {
            System.out.println("ERROR: APK file not found at " + app.getAbsolutePath());
            // Try fallback - check current directory directly
            app = new File("app-debug.apk");
            System.out.println("DEBUG: Fallback App path = " + app.getAbsolutePath());
        }

        if (!app.exists()) {
            throw new RuntimeException("APK file missing! Please ensure app-debug.apk is in the project root.");
        }

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("emulator-5554")
                .setApp(app.getAbsolutePath())
                .setAppPackage("com.example.welcomenote")
                .setAppActivity(".MainActivity")
                .setNoReset(false)
                .setNewCommandTimeout(Duration.ofSeconds(300));

        try {
            driver = new AndroidDriver(
                    URI.create("http://127.0.0.1:4723").toURL(),
                    options
            );
            System.out.println("DEBUG: Driver initialized successfully");
        } catch (MalformedURLException e) {
            System.err.println("FATAL ERROR: Invalid Appium server URL!");
            throw new RuntimeException("Invalid Appium URL", e);
        } catch (Exception e) {
            System.err.println("ERROR: Failed to initialize driver: " + e.getMessage());
            e.printStackTrace();
            throw e; // rethrow because test cannot continue without driver
        }

    }*/

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
