package com.hill.mobile;

import com.hill.common.utilities.cmd.CommandRunner;
import com.hill.common.utilities.cmd.Commands;
import com.hill.mobile.core.MDManager;
import com.hill.mobile.core.PropertiesMobile;
import com.hill.mobile.utilities.ScreenFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


public class BaseMobileTest {
    protected ScreenFactory factory;

    protected boolean isDebug() {
        return Boolean.parseBoolean(PropertiesMobile.getProperty("debug.mod"));
    }

    protected AppiumDriver<MobileElement> driver() {
        return MDManager.getDriver();
    }

    @BeforeAll
    public void setup() {
        factory = new ScreenFactory(driver());
        if (!isDebug()) CommandRunner.runAdb(Commands.ADB.HOME);
    }

    @AfterAll
    public void tearDown() {
        if (driver() != null) {
            driver().quit();
        }
    }

    protected <T> T currentPage(Class<T> clazz) {
        return factory.init(clazz);
    }
}
