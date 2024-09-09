package com.hill.mobile.core;


import com.hill.common.utilities.CU;
import com.hill.common.utilities.cmd.CommandRunner;
import com.hill.common.utilities.cmd.Commands;
import com.hill.common.utilities.cmd.RunHelperMobile;
import com.hill.mobile.utilities.ConstMobile;
import com.hill.mobile.utilities.TimeoutsMobile;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class MDManager {
    private static AppiumDriver<MobileElement> driver;

    private static AppiumDriver<MobileElement> setDriver(String device) {
        statusCheck();
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
            log.error("Session was not created");
        }

        return driver;
    }


    public static AppiumDriver<MobileElement> getDriver() {
        String browser = PropertiesMobile.getProperty("device");
        if (driver == null) driver = setDriver(browser);
        return driver;
    }

    private static void statusCheck() {
        if (!RunHelperMobile.isAppiumOk()) {
            log.info("Starting Appium server");
            CommandRunner.runBash(String.format(Commands.Mobile.APPIUM_RUN, ConstMobile.LOG_DIR));
            CU.Wait.forSeconds(TimeoutsMobile.APPIUM_START);
        }
        if (RunHelperMobile.isAppiumOk()) log.info("Appium server: OK");
        else throw new RuntimeException("Appium server starting failure");

        if (!RunHelperMobile.isEmulatorOk()) {
            log.info("Starting Emulator");
            CommandRunner.runBash(String.format(Commands.Mobile.EMULATOR_RUN, ConstMobile.LOG_DIR));
            CU.Wait.forSeconds(TimeoutsMobile.EMULATOR_START);
        }
        if (RunHelperMobile.isEmulatorOk()) log.info("Emulator: OK");
        else throw new RuntimeException("Emulator starting failure");
    }
}
