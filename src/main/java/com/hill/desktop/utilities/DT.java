package com.hill.desktop.utilities;

import com.hill.desktop.core.DDManager;
import com.hill.mobile.utilities.Messages;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public class DT {

    protected static WindowsDriver<WindowsElement> driver() {
        return DDManager.getDriver();
    }

    public static Actions acts() {
        return new Actions(driver());
    }

    public static class Wait {
        public static void until(String message, Callable<Boolean> c, int timeout) {
            List<Class<? extends Exception>> ignoring = Arrays.asList(
                    StaleElementReferenceException.class,
                    ElementClickInterceptedException.class,
                    ElementNotInteractableException.class);

            getSettableWait(message, timeout, ignoring).until(driver -> {
                try {
                    return c.call();
                } catch (Exception ignored) {
                    return false;
                }
            });
        }

        private static FluentWait<WebDriver> getSettableWait(String msg, int timeout, Collection<Class<? extends Exception>> ignoring) {
            return new WebDriverWait(driver(), timeout).withMessage(msg).ignoreAll(ignoring);
        }

        public static void forSeconds(int seconds) {
            forMillis(seconds * 1000);
        }

        public static void forMillis(int millis) {
            try {
                Thread.sleep(millis);
                //TimeUnit.MILLISECONDS.sleep(millis);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static class Is {

        public static boolean displayed(WindowsElement e) {
            try {
                return e.isDisplayed();
            } catch (Exception ignored) {
                return false;
            }
        }

        public static boolean displayed(String locator) {
            List<WindowsElement> ll = Get.elementsByName(locator);
            return !ll.isEmpty() && ll.get(0).isDisplayed();
        }
    }

    public static class Get {
        public static WindowsElement elementByName(String locator) {
            List<WindowsElement> asList = elementsByName(locator);
            if (asList.isEmpty())
                throw new NoSuchElementException(Messages.noSuchElement(locator));
            return asList.get(0);
        }

        public static List<WindowsElement> elementsByName(String locator) {
            Wait.until("element not found",
                    () -> !driver().findElements(By.name(locator)).isEmpty(),
                    5);
            return driver().findElements(By.name(locator));
        }
    }

}
