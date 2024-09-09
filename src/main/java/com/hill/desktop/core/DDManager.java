package com.hill.desktop.core;

import com.hill.common.utilities.CU;
import com.hill.common.utilities.cmd.CommandRunner;
import com.hill.common.utilities.cmd.Commands;
import com.hill.common.utilities.cmd.RunHelperDesktop;
import com.hill.desktop.appobjects.NotepadObject;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DDManager {
    private static WindowsDriver<WindowsElement> driver;

    private static WindowsDriver<WindowsElement> setDriver(String app) {
        statusCheck();
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", app);
            capabilities.setCapability("platformName", "Windows");
            capabilities.setCapability("deviceName", "WindowsPC");

            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            log.error("Session was not created");
        }

        return driver;
    }

    public static WindowsDriver<WindowsElement> getDriver() {
        String app = NotepadObject.app;
        if (driver == null) driver = setDriver(app);
        return driver;
    }

    public static void tearDown() {
        driver.quit();
        CommandRunner.runPS(Commands.PS.WIN_DRIVER_STOP);
    }

    private static void statusCheck() {
        if (!RunHelperDesktop.isPort4723Free()) throw new RuntimeException("Port is busy");
        if (!RunHelperDesktop.isWinDriverOk()) {
            CommandRunner.runPS(Commands.PS.WIN_DRIVER_START);
            CU.Wait.forMillis(1000);
        }
        if (RunHelperDesktop.isWinDriverOk()) {
            log.info("WindowsDriver: ok");
        } else {
            throw new RuntimeException("WindowsDriver starting failure");
            //check if 'Developer Mode' enabled
        }

    }
}
