package com.hill.desktop.core;

import com.hill.desktop.appobjects.NotepadObject;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ADManager {
    private static WindowsDriver<WindowsElement> driver;

    private static final Logger logger = LoggerFactory.getLogger(ADManager.class);


    public static WindowsDriver<WindowsElement> setDriver(String app) {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", app);
            capabilities.setCapability("platformName", "Windows");
            capabilities.setCapability("deviceName", "WindowsPC");

            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            logger.error("Session was not created");
        }

        return driver;
    }

    public static WindowsDriver<WindowsElement> getDriver() {
        String app = NotepadObject.app;
        // "C:\\Windows\\System32\\notepad.exe"; //todo
        if (driver == null) driver = setDriver(app);
        return driver;
    }
}

