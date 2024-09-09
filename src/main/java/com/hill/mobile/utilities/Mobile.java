package com.hill.mobile.utilities;

import com.hill.mobile.core.MDManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class Mobile {

    public static AppiumDriver<MobileElement> driver() {
        return MDManager.getDriver();
    }

    public static AndroidDriver<MobileElement> driverAndroid() {
        return (AndroidDriver<MobileElement>) driver();
    }

    public static Actions acts() {
        return new Actions(driver());
    }

    public static JavascriptExecutor jse() {
        return (JavascriptExecutor) driver();
    }

    public static void scrollAndClickByText(String visibleText) {
        driverAndroid().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + visibleText + "\").instance(0))").click();
    }

    public static void printPageSource() {
        String pageSource = Mobile.driver().getPageSource();
        System.out.println(pageSource);
    }

    public static class Scroll {
        public static void slide(int offset) {
            acts().moveToElement(Get.elementBy("android:id/content"))
                    .clickAndHold().moveByOffset(0, offset).release()
                    .build().perform();
        }

        public static void slide(MobileElement drag, MobileElement drop) {
            //if both are visible
            acts().dragAndDrop(drag, drop).build().perform();
        }

        private static boolean scrollToTextNoTimeout(String visibleText) {
            return Is.displayed(byTextAndGet(visibleText));
        }

        public static void toText(String visibleText, int timeout) {
            Wait.until(Messages.notFoundDuring(visibleText, timeout),
                    () -> scrollToTextNoTimeout(visibleText),
                    timeout);
        }

        public static void toText(String visibleText) {
            Wait.until("", () -> scrollToTextNoTimeout(visibleText), TimeoutsMobile.SCROLL);
        }

        public static MobileElement byTextAndGet(String visibleText) {
            return driverAndroid().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + visibleText + "\").instance(0))");
        }
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

        public static void until(String message, Callable<Boolean> c) {
            until(message, c, TimeoutsMobile.AFTER_CLICK);
        }

        public static void notStrict(String message, Callable<Boolean> c, int timeout) {
            try {
                until(message, c, timeout);
            } catch (Exception e) {
                log.info(message);
            }
        }
    }

    public static class Get {
        public static String locator(MobileElement e) {
            String s = e.toString();
            String locator;

            if (s.contains("-> xpath: ")) {
                locator = s.split("-> xpath: ")[1].replace("]]", "]");
            } else if (s.contains("uiautomator: new UiScrollable")) {
                locator = s.split("-> -android uiautomator: ")[1]
                        .replace("]", "");
            } else if (s.contains("id: ")) {
                locator = s.split("id: ")[1];
            } else throw new RuntimeException(String.format("Can't get locator <---%s--->", s));

            return String.format("\"%s\"", locator);
        }

        public static MobileElement elementBy(String locator) {
            List<MobileElement> asList = elementsBy(locator);
            if (asList.isEmpty())
                throw new NoSuchElementException(Messages.noSuchElement(locator));
            return asList.get(0);
        }

        public static List<MobileElement> elementsBy(String locator) {
            List<MobileElement> list = new ArrayList<>();
            if (locator.contains("new UiSelector()")) {
                list.addAll(driverAndroid().findElementsByAndroidUIAutomator(locator));
            } else if (locator.contains(":id/")) {
                list.addAll(driver().findElements(By.id(locator)));
            } else if (locator.startsWith("//")) {
                list.addAll(driver().findElements(By.xpath(locator)));
            }
            return list;
        }

        public static MobileElement byXpathUnderElement(MobileElement e, String xpath) {
            return byXpathUnderElementList(e, xpath).get(0);
        }

        public static List<MobileElement> byXpathUnderElementList(MobileElement e, String xpath) {
            return e.findElements(By.xpath("." + xpath));
        }
    }

    public static class Is {

        public static boolean displayed(MobileElement e) {
            try {
                return e.isDisplayed();
            } catch (Exception ignored) {
                return false;
            }
        }

        public static boolean displayed(String locator) {
            List<MobileElement> ll = Get.elementsBy(locator);
            return !ll.isEmpty() && ll.get(0).isDisplayed();
        }
    }
}
