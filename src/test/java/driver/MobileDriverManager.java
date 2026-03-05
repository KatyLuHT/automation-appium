package driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.AppiumDriver;
import resources.config.AndroidConfig;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class MobileDriverManager {

    private static AppiumDriver driver;

    public static AppiumDriver getDriver() {

        if (driver != null && driver.getSessionId() != null) {
            return driver;
        }

        try {
            String appPathValue = AndroidConfig.getRequired("app.path");
            String appiumServerUrl = AndroidConfig.getRequired("appium.server.url");
            String platformName = AndroidConfig.getRequired("platform.name");
            String deviceName = AndroidConfig.getRequired("device.name");
            String automationName = AndroidConfig.getRequired("automation.name");

            Path appPath = Path.of(appPathValue);
            if (!Files.exists(appPath)) {
                throw new IllegalStateException("No se encontro el APK en: " + appPathValue);
            }

            UiAutomator2Options options = new UiAutomator2Options();

            options.setPlatformName(platformName);
            options.setDeviceName(deviceName);
            options.setAutomationName(automationName);

            options.setApp(appPathValue);
            options.setAppWaitActivity("*");

            driver = new AndroidDriver(
                    new URL(appiumServerUrl),
                    options
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "No se pudo iniciar sesion Appium"
                            + e.getMessage(),
                    e
            );
        }

        return driver;
    }

    public static void quitDriver() {

        if(driver != null){
            try {
                driver.quit();
            } finally {
                driver = null;
            }
        }

    }
}