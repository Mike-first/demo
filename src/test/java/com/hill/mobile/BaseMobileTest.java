package com.hill.mobile;

import com.hill.mobile.core.MDManager;
import com.hill.mobile.core.PropertiesMobile;
import com.hill.mobile.utilities.ScreenFactory;
import com.hill.mobile.utilities.bash.CommandRunner;
import com.hill.mobile.utilities.bash.Commands;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseMobileTest {
    protected ScreenFactory factory;

    protected boolean isDebug() {
        return Boolean.parseBoolean(PropertiesMobile.getProperty("debug.mod"));
    }

    protected AppiumDriver<MobileElement> driver() {
        return MDManager.getDriver();
    }

    @BeforeClass
    public void setup() {
        factory = new ScreenFactory(driver());
        if (!isDebug()) CommandRunner.runAdb(Commands.ADB.HOME);
    }

    @AfterClass
    public void tearDown() {
        if (driver() != null) {
            driver().quit();
        }
    }

    protected <T> T currentPage(Class<T> clazz) {
        return factory.init(clazz);
    }
}
