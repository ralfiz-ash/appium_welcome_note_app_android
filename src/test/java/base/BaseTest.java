package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected AppiumDriver driver;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        System.out.println("DEBUG: setUp() called for Sauce Labs");

        // ------------------ SAUCE LABS CONFIGURATION ------------------
        String username = System.getenv("SAUCE_USERNAME"); // Set these in your environment
        String accessKey = System.getenv("SAUCE_ACCESS_KEY");
        String sauceUrl = "@ondemand.us-west-1.saucelabs.com:443/wd/hub";

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("Android GoogleAPI Emulator");
        options.setPlatformVersion("12.0");
        options.setApp("storage:filename=app-debug.apk"); // Assuming uploaded to Sauce Storage
        options.setAppPackage("com.example.welcomenote");
        options.setAppActivity(".MainActivity");

        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("username", username != null ? username : "YOUR_SAUCE_USERNAME");
        sauceOptions.put("accessKey", accessKey != null ? accessKey : "YOUR_SAUCE_ACCESS_KEY");
        sauceOptions.put("build", "Appium-POC-Build");
        sauceOptions.put("name", "Welcome Note POC Test");
        options.setCapability("sauce:options", sauceOptions);

        URL url = new URL(
                "https://" + sauceOptions.get("username") + ":" + sauceOptions.get("accessKey") + sauceUrl);

        try {
            driver = new AndroidDriver(url, options);
            System.out.println("DEBUG: Sauce Labs Driver initialized successfully");
        } catch (Exception e) {
            System.err.println("ERROR: Failed to initialize Sauce Labs driver: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        /*
         * // ------------------ LOCAL CONFIGURATION ------------------
         * System.out.println("DEBUG: Running locally");
         * String userDir = System.getProperty("user.dir");
         * File app = new File(userDir, "src/apk/app-debug.apk");
         * if (!app.exists()) {
         * app = new File(userDir, "app-debug.apk");
         * }
         * 
         * UiAutomator2Options localOptions = new UiAutomator2Options()
         * .setPlatformName("Android")
         * .setAutomationName("UiAutomator2")
         * .setDeviceName("emulator-5554")
         * .setAppPackage("com.example.welcomenote")
         * .setAppActivity(".MainActivity")
         * .setNoReset(false);
         * 
         * if (app.exists()) {
         * localOptions.setApp(app.getAbsolutePath());
         * }
         * 
         * driver = new AndroidDriver(new java.net.URL("http://127.0.0.1:4723"),
         * localOptions);
         */
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
