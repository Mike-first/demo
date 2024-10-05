package com.hill.mobile.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class MDManager {
    private static AppiumDriver<MobileElement> driver;
    private static final Logger logger = LoggerFactory.getLogger(MDManager.class);

    public static AppiumDriver setDriver(String device) {
        try {
            switch (device) {

                case "MyEmulator": {
                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    capabilities.setCapability("deviceName", "MyEmulator");
                    capabilities.setCapability("platformName", "Android");
                    capabilities.setCapability("platformVersion", "15");
                    capabilities.setCapability("appium:automationName", "UiAutomator2");
                    driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/"), capabilities);
                    break;
                }

                case "iPhone15": {
                    throw new RuntimeException("to be implemented");
                }

                default:
                    throw new IllegalArgumentException("Please specify valid mobile device name.");

            }
        } catch (MalformedURLException e) {
            logger.error("Session was not created");
        }

        return driver;
    }


    public static AppiumDriver getDriver() {
        String browser = PropertiesMobile.getProperty("device");
        if (driver == null) driver = setDriver(browser);
        return driver;
    }
}
