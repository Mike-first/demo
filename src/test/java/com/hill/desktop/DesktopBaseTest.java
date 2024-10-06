package com.hill.desktop;

import com.hill.desktop.core.ADManager;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class DesktopBaseTest {
    protected static WindowsDriver<WindowsElement> driver() {
        return ADManager.getDriver();
    }

    @BeforeClass
    public void setup() {
        driver();
        System.out.println("driver initialized");
    }

    @AfterClass
    public void tearDown() {
        driver().quit();
    }
}
