package com.hill.desktop;

import com.hill.desktop.core.DDManager;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class DesktopBaseTest {
    protected final Logger logger = LoggerFactory.getLogger(DesktopBaseTest.class);

    protected static WindowsDriver<WindowsElement> driver() {
        return DDManager.getDriver();
    }

    @BeforeClass
    public void setup() {
        driver();
        logger.info("driver initialized");
    }

    @AfterClass
    public void tearDown() {
        driver().quit();
    }
}
