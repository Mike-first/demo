package com.hill.web.utilities;


import com.hill.web.core.WDManager;
import com.hill.web.pageobjects.blocks.Header;
import com.hill.web.utilities.constants.Messages;
import com.hill.web.utilities.constants.Timeouts;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Web {

    private Web() {
        throw new IllegalStateException(Messages.UTILITY_CLASS);
    }


    public static WebDriver driver() {
//        return WebDriverRunner.getWebDriver(); //for Selenide
        return WDManager.getDriver(); //for Selenium
    }

    public static JavascriptExecutor jse() {
        return (JavascriptExecutor) driver();
    }

    public static Actions acts() {
        return new Actions(driver());
    }

    public static void navigateToPage(String url) {
        log.debug("open link " + url);
        driver().get(url);
    }

    public static class Scroll {
        private Scroll() {
            throw new IllegalStateException(Messages.UTILITY_CLASS);
        }

        public static WebElement toView(WebElement e) {
            jse().executeScript("arguments[0].scrollIntoView()", e);
            return e;
        }

        public static void forPixels(int h) {
            jse().executeScript(String.format("window.scrollBy(0, %d)", h), "");
        }

        public static void toBottom() {
            scrollJS("document.body.scrollHeight");
        }

        public static void toTop() {
            scrollJS("-document.body.scrollHeight");
        }

        private static void scrollJS(String arg) {
            jse().executeScript(String.format("window.scrollTo(0, %s)", arg));
        }

//        public static void forElementToBeClickable(WebElement element) {
//            if (!Is.elementInClickableAria(element)) {
//                Scroll.toView(element);
//            }
//        }

        public static WebElement forElementToBeUnderHeader(WebElement e) {
            log.debug(String.format("User scrolls until %s to be under header ", Get.locator(e)));
            toView(e);
            forPixels(-Header.getBottomPosition());
            return e;
        }

//        public static void toElementByOffset(WebElement e, int y) {
//            acts().moveToElement(e).moveByOffset(0, y).build().perform();
//        }


//        public static void toElement(WebElement e) {
//            acts().moveToElement(e).build().perform();
//        }

//        public static void toElement(By locator) {
//            toElement(driver().findElement(locator));
//        }

//        public static void toElement(String path) {
//            toElement(By.xpath(path));
//        }
//
//        public static void toElement3(WebElement element) {
//            int y = element.getLocation().getY();
//            jse().executeScript("window.scrollTo(0," + y + ")");
//        }
//
//        public static void toElement2(By locator) {
//            WebElement element = driver().findElement(locator);
//            int y = element.getLocation().getY();
//            jse().executeScript("window.scrollTo(0," + y + ")");
//        }

    }//class Scroll

    public static class Wait {
        private Wait() {
            throw new IllegalStateException(Messages.UTILITY_CLASS);
        }

        public static void pageLoading() {
            pageLoading(false);
        }

        public static void pageLoading(boolean isExtended) {
            log.debug("wait page loading");
            FluentWait<WebDriver> wait = new WebDriverWait(driver(), Timeouts.PAGE_LOADING)
                    .withMessage("Page was not loaded in " + Timeouts.PAGE_LOADING)
                    .ignoring(StaleElementReferenceException.class);
            wait.until(d -> Is.pageLoaded(isExtended));
        }

        public static void forSeconds(int seconds) {
            log.debug("wait for seconds" + seconds);
            forMillis(seconds * 1000);
        }

        public static void forMillis(int millis) {
            log.debug("wait for millis " + millis);
            try {
                TimeUnit.MILLISECONDS.sleep(millis);
            } catch (InterruptedException ignored) {
            }
        }

        public static WebElement elementHaveText(By locator, String text, int timeout) {
            WebDriverWait wait = new WebDriverWait(driver(), timeout); //Duration.ofSeconds(timeout)
            wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
            return driver().findElement(locator);
        }

        public static void until(String message, Callable<Boolean> c) {
            until(message, c, Timeouts.AFTER_CLICK);
        }

        public static void until(String message, Callable<Boolean> c, int timeout) {
            log.debug("wait until");
            FluentWait<WebDriver> wait = new WebDriverWait(driver(), timeout).withMessage(message);
            wait.until(driver -> {
                try {
                    return c.call();
                } catch (Exception ignored) {
                    return false;
                }
            });
        }

        private static FluentWait<WebDriver> getWait() {
            return new WebDriverWait(driver(), Timeouts.CLICK)
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(ElementClickInterceptedException.class)
                    .ignoring(ElementNotInteractableException.class);
        }

        public static WebElement elementClickability(WebElement e) {
            getWait().until(ExpectedConditions.elementToBeClickable(e));
            return e;
        }

        public static WebElement elementVisibility(WebElement e) {
            FluentWait<WebDriver> wait = new WebDriverWait(driver(), Timeouts.ELEMENT_VISIBILITY)
                    .ignoring(StaleElementReferenceException.class);
            wait.until(ExpectedConditions.visibilityOf(e));
            return e;
        }

        public static void elementVisibility(String xpath) {
            FluentWait<WebDriver> wait = new WebDriverWait(driver(), Timeouts.ELEMENT_VISIBILITY)
                    .ignoring(StaleElementReferenceException.class);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        }

        public static boolean waitNotStrict(String message, Callable<Boolean> c, int timeout) {
            log.debug("wait not strict");
            try {
                until(message, c, timeout);
                return true;
            } catch (Exception e) {
                log.debug(message);
                return false;
            }
        }

        public static void elementInvisibility(String xpath) {
            FluentWait<WebDriver> wait = new WebDriverWait(driver(), Timeouts.ELEMENT_VISIBILITY);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
        }

        public static void elementInvisibility(WebElement e) {
            FluentWait<WebDriver> wait = new WebDriverWait(driver(), Timeouts.ELEMENT_VISIBILITY);
            wait.until(ExpectedConditions.invisibilityOf(e));
        }

    }//class Wait

    public static class Click {
        private Click() {
            throw new IllegalStateException(Messages.UTILITY_CLASS);
        }

        public static void click(WebElement element) {
            StringBuilder log = new StringBuilder("Click " + Get.locator(element));
//            if (!Is.elementInClickableAria(element)) {
//                Scroll.forElementToBeUnderHeader(element);
//            }
//            Wait.forMillis(300);
            Scroll.forElementToBeUnderHeader(element);
            boolean isPrevClickedWasSuccessful = false;
            try {
                element.click();
                isPrevClickedWasSuccessful = true;
            } catch (Exception exSimple) {
                log.append("; Simple click failed; ");
            }
            if (!isPrevClickedWasSuccessful) {
                try {
                    log.append("Try click by Actions; ");
                    actions(element);
                    log.append("Click by Actions successful.");
                    isPrevClickedWasSuccessful = true;
                } catch (Exception exActions) {
                    log.append("Click by Actions failed; ");
                }
            }
            if (!isPrevClickedWasSuccessful) {
                try {
                    log.append("Try click by JS; ");
                    js(element);
                    log.append("Click by JS successful.");
                    isPrevClickedWasSuccessful = true;
                } catch (Exception exJx) {
                    log.append("Click by JS failed; ");
                }
            }
            if (!isPrevClickedWasSuccessful) {
                try {
                    log.append("Try complex click; ");
                    withScrolling(element);
                    log.append("Complex click successful.");
                } catch (Exception exComplex) {
                    log.append("Click failed.");
                    Web.log.debug(log.toString());
                    throw new RuntimeException("click element '" + element + "' failed");
                }
            }
            Web.log.debug(log.toString());
            Wait.forMillis(100);
        }

        public static void simple(WebElement e) {
            e.click();
        }

        public static void js(WebElement element) {
            jse().executeScript("arguments[0].click()", element);
        }

        public static void actions(WebElement element) {
            acts().moveToElement(element).click(element).build().perform();
        }

        public static void withScrolling(WebElement e) {
            Wait.pageLoading();
            Wait.getWait().withMessage("Click failed").until(driver -> {
                Scroll.forElementToBeUnderHeader(e);
                js(e);
                return true;
            });
        }

        public static void blind(String xpath, int offsetY, int offsetX) {
            blind(xpath, offsetY, offsetX, false);
        }

        public static void blind(String xpath, int offsetY, int offsetX, boolean isDebug) {
            WebElement e = Get.element(xpath);
            Actions act = acts().moveToElement(e).moveByOffset(offsetX, offsetY);
            if (isDebug) {
                act.contextClick().build().perform();
            } else {
                act.click().build().perform();
            }
        }

        public static void clickUntouchable(WebElement el, int xOffset, int yOffset) {
            //for before/after pseudo-elements
            acts().moveToElement(el).moveByOffset(xOffset, yOffset).click().build().perform();
        }

        public static void clickUntouchable(WebElement el, int xOffset, int yOffset, Boolean test) {
            //for debug before/after pseudo-elements
            acts().moveToElement(el).moveByOffset(xOffset, yOffset).contextClick().build().perform();
        }

        public static void clickSimple(String path) {
            (driver().findElement(By.xpath(path))).click();
        }

        public static void clickSimpleIfPresent(String path) {
            if (Get.presentedElementsCount(By.xpath(path))) (driver().findElement(By.xpath(path))).click();
        }

    }//class Click

    //tab related methods
    public static class Tab {
        private Tab() {
            throw new IllegalStateException(Messages.UTILITY_CLASS);
        }

        public static void switchToNewWindowPage() {
            String currentHandle = driver().getWindowHandle();
            for (String winHandle : driver().getWindowHandles()) {
                if (!winHandle.equalsIgnoreCase(currentHandle)) {
                    driver().switchTo().window(winHandle);
                    return;
                }
            }
        }

        public static void refreshBrowser() {
            final String CURRENT_URL = driver().getCurrentUrl();
            String[] tabs = openNewTab();
            driver().switchTo().window(tabs[0]).close();
            driver().switchTo().window(tabs[1]);
            driver().get(CURRENT_URL);
        }

        public static void refreshPage() {
            String url = driver().getCurrentUrl();
            driver().get(url);
        }

        public static String[] expectOpensInNewTabWhenClick(WebElement e) {
            return openInNewTab(() -> Click.withScrolling(e));
        }

        public static String[] openNewTab() {
            return openInNewTab(() -> jse().executeScript("window.open()"));
        }

        public static String[] openInNewTab(Runnable action) {
            String startTab = driver().getWindowHandle();
            String newTab = null;
            Set<String> prevTabs = driver().getWindowHandles();
            action.run();
            Set<String> newTabs = driver().getWindowHandles();
            if (newTabs.size() != prevTabs.size() + 1) {
                throw new RuntimeException("Unexpected tab count");
            }
            for (String tab : newTabs) {
                if (!prevTabs.contains(tab)) {
                    newTab = tab;
                    break;
                }
            }
            driver().switchTo().window(newTab);
            return new String[]{startTab, newTab};
        }

        public static String tabTitle() {
            return driver().getTitle();
        }

        public static int tabsCount() {
            Set<String> tabs = driver().getWindowHandles();
            return tabs.size();
        }

        public static String currentURL() {
            return driver().getCurrentUrl();
        }

        public static String curWindow() {
            return driver().getWindowHandle();
        }

        public static String justOpenedTabGetUrlAndClose(WebElement e) {
            String startTab = driver().getWindowHandle();
            String newTab = null;
            Set<String> oldTabs = driver().getWindowHandles();
            e.click();
            Set<String> newTabs = driver().getWindowHandles();
            if (newTabs.size() != oldTabs.size() + 1) {
                throw new RuntimeException("MORE THAN ONE TAB WAS OPENED");
            }
            for (String tab : newTabs) {
                newTab = tab;
                if (!oldTabs.contains(newTab)) {
                    break;
                }
            }
            driver().switchTo().window(newTab);
            String result = driver().getCurrentUrl();
            driver().close();
            driver().switchTo().window(startTab);
            return result;
        }

        //todo refactor methods above and under
        public static String[] openNewTab(WebElement e) {
            //Assertions.assertTrue(e.getAttribute(KW.TARGET).contains("blank"));
            String startTab = driver().getWindowHandle();
            String newTab = null;
            Set<String> oldTabs = driver().getWindowHandles();
            e.click();
            Set<String> newTabs = driver().getWindowHandles();
            if (newTabs.size() != oldTabs.size() + 1) {
                throw new RuntimeException("MORE THAN ONE TAB WAS OPENED");
            }
            for (String tab : newTabs) {
                newTab = tab;
                if (!oldTabs.contains(newTab)) {
                    break;
                }
            }
            driver().switchTo().window(newTab);
            return new String[]{startTab, newTab};
        }

        public static String[] openNewTab(String path) {
            return openNewTab(driver().findElement(By.xpath(path)));
        }

        public static String[] openNewTab(By locator) {
            return openNewTab(driver().findElement(locator));
        }

        public static void slide(WebElement drag, WebElement drop) {
            acts().dragAndDrop(drag, drop).build().perform();
        }

    }//class Tab

    //gets elements/locators
    public static class Get {
        private Get() {
            throw new IllegalStateException(Messages.UTILITY_CLASS);
        }

        public static String locator(WebElement e) {
            String s = e.toString();
            String locator;
            if (s.contains("xpath")) {
                locator = s.split("xpath: ")[1].replace("]]", "]");
            } else if (s.contains("css selector")) {
                locator = s.split("css selector: ")[1]
                        .replace("'", "")
                        .replace("]", "");
            } else throw new RuntimeException(String.format("Can't get locator [%s]", s));
            return String.format("\"%s\"", locator);
        }

        public static WebElement element(String xpath) {
            List<WebElement> asList = elements(xpath);
            if (asList.isEmpty())
                throw new NoSuchElementException(Messages.noSuchElement(xpath));
            return elements(xpath).get(0);
        }

        public static List<WebElement> elements(String xpath) {
            return driver().findElements(By.xpath(xpath));
        }

        public static WebElement elementUnder(WebElement e, String xpath) {
            return elementsUnder(e, xpath).get(0);
        }

        public static List<WebElement> elementsUnder(WebElement e, String xpath) {
            return e.findElements(By.xpath("." + xpath));
        }

        public static String text(WebElement e) {
            return Wait.elementVisibility(e).getText().trim();
        }

        public static boolean presentedElementsCount(By locator) {
            //https://stackoverflow.com/questions/12270092/best-way-to-check-that-element-is-not-present-using-selenium-webdriver-with-java
            try {
                driver().findElement(locator);
                return true;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                return false;
            }
        }

        public static String allCss(By locator) {
            //https://stackoverflow.com/questions/32537339/getting-the-values-of-all-the-css-properties-of-a-selected-element-in-selenium
            WebElement we = driver().findElement(locator);
            String script = "var s = '';" +
                    "var o = getComputedStyle(arguments[0]);" +
                    "for(var i = 0; i < o.length; i++){" +
                    "s+=o[i] + ':' + o.getPropertyValue(o[i])+';';}" +
                    "return s;";
            return "" + jse().executeScript(script, we);
        }
    }//class Get

    //check is<smth> for elements
    public static class Is {
        private Is() {
            throw new IllegalStateException(Messages.UTILITY_CLASS);
        }

        public static boolean elementIntoViewPort(WebElement el) {
            return (boolean) jse().executeScript("let elem = arguments[0],"
                    + "  box = elem.getBoundingClientRect(),    "
                    + "  axisx = box.left + box.width / 2,         "
                    + "  axisy = box.top + box.height / 2,         "
                    + "  e = document.elementFromPoint(axisx, axisy); "
                    + "for (; e; e = e.parentElement) {         "
                    + "  if (e === elem)                        "
                    + "    return true;                         "
                    + "}                                        "
                    + "return false;                            ", el);
        }

        public static boolean elementDisplayed(WebElement e) {
            //does not work for invisible elements. use elementPresent(String xpath)
            try {
                return e.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }

        public static boolean elementDisplayed(By by) {
            try {
                return elementDisplayed(driver().findElement(by));
            } catch (Exception ex) {
                return false;
            }
        }

        public static boolean elementDisplayed(String xpath) {
            try {
                return elementDisplayed(By.xpath(xpath));
            } catch (Exception ex) {
                return false;
            }
        }

        public static boolean elementPresent(String xpath) {
            return !driver().findElements(By.xpath(xpath)).isEmpty();
        }

//        public static boolean elementInClickableAria(WebElement e) {
//            Dimension window = driver().manage().window().getSize();
//            int y = Header.getBottomPosition() + 10;
//            Rectangle clickableRect = new Rectangle(0, y, window.height, window.width);
//            Rectangle elementRect = e.getRect();
//            return (elementRect.y - 10) >= clickableRect.y && (elementRect.y + elementRect.height + 10) <= clickableRect.height;
//        }

        public static boolean elementIntractable(WebElement e) {
            return e.isDisplayed() && e.isEnabled();
        }

        public static boolean pageLoaded(boolean isExtended) {
            boolean res = jse().executeScript("return document.readyState").equals("complete");
            if (isExtended) {
                String[] paths = new String[]{"//div[@class='-loading-inner']", "//div[@class='loader']",
                        "//*[text()='Loading...']", "//*[text()='Processing...']",
                        "//div[contains(@class, 'ProgressIndicator')]"};
                for (String s : paths) {
                    res &= Get.elements(s).stream().noneMatch(WebElement::isDisplayed);
                }
            }
            return res;
        }

    }//class Is

    //element related method
    public static class Element {
        private Element() {
            throw new IllegalStateException(Messages.UTILITY_CLASS);
        }

        public static void moveToElement(WebElement element) {
            acts().moveToElement(element).click().perform();
        }

        public static void enterText(WebElement e, String text, boolean clear) {
            e = Wait.elementClickability(e);
            if (clear) {
                e.clear();
            }
            e.sendKeys(text);
        }

        //Drop-down
        public static void selectDropdownValueByText(WebElement element, String dropdownList) {
            Select select = new Select(element);
            select.selectByVisibleText(dropdownList);
        }


        public static void displayMapArrayList(Map<String, ArrayList<String>> map) {
            for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                String key = entry.getKey();
                ArrayList<String> val = entry.getValue();
                log.debug(String.format("%s=%s", key, val));
            }
        }

        public static void dragAndDropToUpload(Path fileToUpload, String inputPath) {
            WebElement input = Get.element(inputPath);
            Wait.until(Messages.INPUT_NOT_ENABLED, input::isEnabled);
            input.sendKeys(fileToUpload.toString());
            Wait.pageLoading();
        }

        public static int elementsCount(String path) {
            return els(path).size();
        }

        public static String[] titlesCollection(List<WebElement> c) {
            String[] res = new String[c.size()];
            for (int i = 0; i < c.size(); i++) {
                res[i] = c.get(i).getAttribute(KW.INNER_TEXT);
            }
            return res;
        }

        public static By xp(String path) {
            return By.xpath(path);
        }

        public static WebElement el(String path) {
            return driver().findElement(By.xpath(path));
        }

        public static List<WebElement> els(String path) {
            return driver().findElements(By.xpath(path));
        }

    }//class Element

    public static class Link {
        private Link() {
            throw new IllegalStateException(Messages.UTILITY_CLASS);
        }

        public static boolean isLinkThisTab(WebElement e, String href, boolean debug) {
            //for debug
            if (debug) {
                log.debug(String.format("href - %s", (e.getAttribute(KW.HREF).equals(href) ? true : e.getAttribute(KW.HREF))));
                log.debug(String.format("isDisplayed - %s", e.isDisplayed()));
                log.debug(String.format("isEnabled - %s", e.isEnabled()));
                log.debug(String.format("!target:blank - %s", !e.getAttribute(KW.TARGET).contains(KW.BLANK)));
            }
            return isLinkThisTab(e, href);
        }

        public static boolean isLinkThisTab(WebElement e, String href) {
            return e.getAttribute(KW.HREF).equals(href) && e.isDisplayed()
                    && e.isEnabled() && !e.getAttribute(KW.TARGET).contains(KW.BLANK);
        }

        public static boolean isLinkNewTab(WebElement e, String href) {
            return e.getAttribute(KW.HREF).equals(href) && e.isDisplayed() && e.isEnabled()
                    && e.getAttribute(KW.TARGET).contains(KW.BLANK);
        }

        public static boolean isLinkNewTab(WebElement e, String href, boolean debug) {
            if (debug) {
                log.debug(String.format("href - %s", (e.getAttribute(KW.HREF).equals(href) ? true : e.getAttribute(KW.HREF))));
                log.debug(String.format("isDisplayed - %s", e.isDisplayed()));
                log.debug(String.format("isEnabled - %s", e.isEnabled()));
                log.debug(String.format("!target:blank - %s", !e.getAttribute(KW.TARGET).contains(KW.BLANK)));
            }
            return isLinkNewTab(e, href);
        }

        public static boolean isLinkThisTab(String path, String href) {
            WebElement e = driver().findElement(By.xpath(path));
            return e.isDisplayed() && e.isEnabled() && e.getAttribute(KW.HREF).equals(href);
        }

        public static boolean checkLinkPart(WebElement e, String partOfHref) {
            return e.getAttribute(KW.HREF).contains(partOfHref) && e.isDisplayed() && e.isEnabled();
        }

    }

    public static class Hover {
        private Hover() {
            throw new IllegalStateException(Messages.UTILITY_CLASS);
        }

        public static void overElement(WebElement e) {
            acts().moveToElement(e).build().perform();
        }

    }

//    public static void saveScreen(String fileName) {
//        File srcFile = ((TakesScreenshot) driver()).getScreenshotAs(OutputType.FILE);
//        String targetPath = PathStorage.SCREENSHOTS_DIR
//                + FileSystems.getDefault().getSeparator() + fileName
//                + Dates.now().timeStamp()
//                + ".png";
//        log.debug(String.format("Saved screen: %s", targetPath));
//        FileUtils.copy(srcFile.toPath(), Paths.get(targetPath));
//    }

    public static int randomIndex(List l) {
        return random(l.size());
    }

    public static int randomIndex(List l, int min) {
        int r = random(l.size());
        return (r <= min) ? r + 2 : r;
    }

    public static int random(int max) {
        return (int) (Math.random() * max);
    }

    public static ByteArrayInputStream screenAsByteArray() {
        log.debug("Take screenshot");
        return new ByteArrayInputStream(((TakesScreenshot) driver()).getScreenshotAs(OutputType.BYTES));
    }

//    public static void addScreenshot() {
//        log.debug("addScreenshot.Allure.addAttachment");
//        Allure.addAttachment("screenshot3", Web.screenAsByteArray());
//    }
//
//    public static void addText(String name, String s) {
//        log.debug("addText.Allure.attachment");
//        Allure.attachment(name, s);
//    }

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] addScreenshot() {
        log.debug("@Attachment.addScreenshot");
        return ((TakesScreenshot) driver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{type}", type = "text/plain", fileExtension = ".txt")
    public static byte[] addText(String type, String content) {
        log.debug("@Attachment.addText");
        return content.getBytes(StandardCharsets.UTF_8);
    }

    public static void addPic() {
        try (InputStream is = Files.newInputStream(Paths.get("src/test/resources/forTest.jpg"))) {
            Allure.attachment("image.png", is);
        } catch (IOException e) {
            log.warn("failed to add picture");
        }
    }


    public static class KW {
        public static final String CURSOR = "cursor";
        public static final String POINTER = "pointer";
        public static final String HREF = "href";
        public static final String INNER_TEXT = "innerText";
        public static final String TARGET = "target";
        public static final String BLANK = "blank";
        public static final String CLASS = "class";
    }

}
