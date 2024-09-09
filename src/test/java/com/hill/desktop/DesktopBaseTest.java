package com.hill.desktop;

import com.hill.desktop.core.DDManager;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DesktopBaseTest {
    protected final Logger logger = LoggerFactory.getLogger(DesktopBaseTest.class);

    protected static WindowsDriver<WindowsElement> driver() {
        return DDManager.getDriver();
    }

    @BeforeAll
    public void setup() {
        driver();
        logger.info("driver initialized");
    }

    @AfterAll
    public void tearDown() {
        driver().quit();
    }
}
