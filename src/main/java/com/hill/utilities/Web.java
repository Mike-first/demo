package com.hill.utilities;

import com.hill.core.Assertions;
import com.hill.core.WDManager;
import com.hill.pageobjects.blocks.Header;
import com.hill.utilities.constants.Messages;
import com.hill.utilities.constants.PathStorage;
import com.hill.utilities.constants.Timeouts;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class Web {

    private Web() {
        throw new IllegalStateException(Messages.UTILITY_CLASS);
    }

    private static final Logger logger = LoggerFactory.getLogger(Web.class);

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
        driver().get(url);
    }

    public static class Scroll {
        private Scroll() {
            throw new IllegalStateException(Messages.UTILITY_CLASS);
        }

        public static void toView(WebElement element) {
            jse().executeScript("arguments[0].scrollIntoView()", element);
        }


        public static void down() {
            jse().executeScript("window.scrollBy(0,500)", "");
        }

        public static void down1000() {
            jse().executeScript("window.scrollBy(0,1000)", "");
        }

        public static void toBottom() {
            jse().executeScript("window.scrollTo(0, document.body.scrollHeight)");
        }

        public static void toTop() {
            jse().executeScript("window.scrollTo(0, -document.body.scrollHeight)");
        }

        public static void forElementToBeClickable(WebElement element) {
            if (!Is.elementInClickableAria(element)) {
                Scroll.toElementJs(element);
            }
        }

        public static WebElement forElementToBeUnderHeader(WebElement e) {
            logger.info(String.format("User scrolls until %s to be under header ", Get.locator(e)));
            int previousPosition = e.getLocation().y;
            int offset = previousPosition - 20; //- NavigationPage.getNavigationBarBottomPosition()
            jse().executeScript(String.format("window.scrollBy(0, %d)", offset), "");
            int millis = 100 + 50 * Math.abs((previousPosition - offset) / 100);
            Wait.forMillis(millis);
            return e;
        }


        public static WebElement toElementJs(WebElement e) {
            jse().executeScript("arguments[0].scrollIntoView()", e);
            return e;
        }

        public static WebElement scrollForElementToBeUnderHeader(WebElement e) {
            logger.info(String.format("User scrolls until %s to be under header ", Get.locator(e)));
            int previousPosition = e.getLocation().y;
            int offset = previousPosition - Header.getBottomPosition() - 20;
            jse().executeScript(String.format("window.scrollBy(0, %d)", offset), "");
            int millis = 100 + 50 * Math.abs((previousPosition - offset) / 100);
            Wait.forMillis(millis);
//            Wait.waitNotStrict("", ()-> null, 1);
            return e;
        }

        public static void toElementByOffset(WebElement e, int y) {
            acts().moveToElement(e).moveByOffset(0, y).build().perform();
        }


        public static void toElement(WebElement e) {
            acts().moveToElement(e).build().perform();
        }

        public static void toElement(By locator) {
            toElement(driver().findElement(locator));
        }

        public static void toElement(String path) {
            toElement(By.xpath(path));
        }

        public static void toElement3(WebElement element) {
            int y = element.getLocation().getY();
            jse().executeScript("window.scrollTo(0," + y + ")");
        }

        public static void toElement2(By locator) {
            WebElement element = driver().findElement(locator);
            int y = element.getLocation().getY();
            jse().executeScript("window.scrollTo(0," + y + ")");
        }

    }//class Scroll

    public static class Wait {
        private Wait() {
            throw new IllegalStateException(Messages.UTILITY_CLASS);
        }

        public static void pageLoading() {
            WebDriverWait wait = new WebDriverWait(driver(), Timeouts.PAGE_LOADING);
            wait.until(driver ->
                    jse().executeScript("return document.readyState").equals("complete"));
//            wait.until(driver -> driver.findElements(By.xpath("//div[contains(@class, 'ProgressIndicator')]")).isEmpty());
        }

        public static void pageLoadingLong() {
//        for difficult cases
            FluentWait<WebDriver> wait = new WebDriverWait(driver(), Timeouts.PAGE_LOADING)
                    .withMessage("Page was not loaded in " + Timeouts.PAGE_LOADING)
                    .ignoring(StaleElementReferenceException.class);
            wait.until(d -> jse().executeScript("return document.readyState").equals("complete")
                    && Is.pageLoaded());
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

        public static WebElement elementHaveText(By locator, String text, int timeout) {
            WebDriverWait wait = new WebDriverWait(driver(), timeout); //Duration.ofSeconds(timeout)
            wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
            return driver().findElement(locator);
        }

        public static void until(String message, Callable<Boolean> c) {
            until(message, c, Timeouts.AFTER_CLICK);
        }

        public static void until(String message, Callable<Boolean> c, int timeout) {
            FluentWait<WebDriver> wait = new WebDriverWait(driver(), timeout).withMessage(message);
            wait.until(driver -> {
                try {
                    return c.call();
                } catch (Exception ignored) {
                    return false;
                }
            });
        }

        private static FluentWait<WebDriver> getClickWait() {
            return new WebDriverWait(driver(), Timeouts.CLICK)
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(ElementClickInterceptedException.class)
                    .ignoring(ElementNotInteractableException.class);
        }

        public static WebElement elementClickability(WebElement e) {
            getClickWait().until(ExpectedConditions.elementToBeClickable(e));
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

        public static void waitNotStrict(String message, Callable<Boolean> c, int timeout) {
            try {
                until(message, c, timeout);
            } catch (Exception e) {
                logger.info(message);
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
            if (!Is.elementInClickableAria(element)) {
                Scroll.scrollForElementToBeUnderHeader(element);
            }
            Wait.forMillis(300);
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
                    complex(element);
                    log.append("Complex click successful.");
                } catch (Exception exComplex) {
                    log.append("Click failed.");
                    logger.info(log.toString());
                    throw new RuntimeException("click element '" + element + "' failed");
                }
            }
            logger.info(log.toString());
            Wait.forMillis(100);
        }

        public static void js(WebElement element) {
            jse().executeScript("arguments[0].click()", element);
        }

        public static void actions(WebElement element) {
            acts().moveToElement(element).click(element).build().perform();
        }

        public static void complex(WebElement e) {
            Wait.pageLoading();
            Wait.getClickWait().withMessage("Click failed").until(driver -> {
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
            return openInNewTab(() -> Click.complex(e));
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
            String startTab = driver().getWindowHandle();//сохраним первоначальную вкладку. она имеет право быть не_единственной / не_первой
            String newTab = null;//обявим переменную под вкладку, которая будет открыта по клику на элемент
            Set<String> oldTabs = driver().getWindowHandles();//возьмем сет всех вкладок открытых до
            e.click();//кликнем
            Set<String> newTabs = driver().getWindowHandles();//возьмем сет всех вкладок открытых после
            if (newTabs.size() != oldTabs.size() + 1) {
                Assert.fail("MORE THAN ONE TAB WAS OPENED");//ожидаем, что вкладок стало на одну больше
            }
            for (String tab : newTabs) {
                newTab = tab;
                //find new tab
                if (!oldTabs.contains(newTab)) {
                    break;
                }
            }
            driver().switchTo().window(newTab);//переключились на новую вкладку
            String result = driver().getCurrentUrl();//взяли УРЛ
            driver().close();
            driver().switchTo().window(startTab);
            return result;
        }//target="blank", если ходить по вкладкам неудобно


        public static String[] openNewTab(String path) {
        /*
        передаем в метод путь на элемент, который откроет новую вкладку по клику
        метод откроет новую вкладку, переключит на нее фокус
        вернет массив [старая, новая] вкладки
        */
            return openNewTab(driver().findElement(By.xpath(path)));
        }

        public static String[] openNewTab(By locator) {
            return openNewTab(driver().findElement(locator));
        }

        public static String[] openNewTab(WebElement e) {
            // можно проверять сразу, собирается ли что-то открываться в новой вкладке или нет, но в таком случае мотод съест только ссылку, а у нас может быть что-то вложенное в ссылку млм обернутое вокруг
            //Assert.assertTrue(e.getAttribute(KW.TARGET).contains("blank"));
            String startTab = driver().getWindowHandle();//сохраним первоначальную вкладку. она имеет право быть не_единственной / не_первой
            String newTab = null;//обявим переменную под вкладку, которая будет открыта по клику на элемент
            Set<String> oldTabs = driver().getWindowHandles();//возьмем сет всех вкладок открытых до
            e.click();//кликнем
            Set<String> newTabs = driver().getWindowHandles();//возьмем сет всех вкладок открытых после
            if (newTabs.size() != oldTabs.size() + 1) {
                Assert.fail("MORE THAN ONE TAB WAS OPENED");//ожидаем, что вкладок стало на одну больше
            }
            for (String tab : newTabs) {
                newTab = tab;//нашли вкладку которой раньше не было
                if (!oldTabs.contains(newTab)) {
                    break;
                }
            }
            driver().switchTo().window(newTab);//переключились на новую вкладку
            return new String[]{startTab, newTab};
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

        public static boolean elementInClickableAria(WebElement e) {
            Dimension window = driver().manage().window().getSize();
            int y = Header.getBottomPosition() + 10;
            Rectangle clickableRect = new Rectangle(0, y, window.height, window.width);
            Rectangle elementRect = e.getRect();
            return (elementRect.y - 10) >= clickableRect.y && (elementRect.y + elementRect.height + 10) <= clickableRect.height;
        }

        public static boolean elementIntractable(WebElement e) {
            return e.isDisplayed() && e.isEnabled();
        }

        public static boolean pageLoaded() {
            boolean res = true;
            String[] paths = new String[]{"//div[@class='-loading-inner']", "//div[@class='loader']",
                    "//*[text()='Loading...']", "//*[text()='Processing...']", "//div[contains(@class, 'ProgressIndicator')]"};
            for (String s : paths) {
                res &= Get.elements(s).stream().noneMatch(WebElement::isDisplayed);
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
                logger.info(String.format("%s=%s", key, val));
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

        public static void checkLinkThisTab(WebElement e, String href, boolean debug) {
            //for debug
            if (debug) {
                logger.info(String.format("href - %s", (e.getAttribute(KW.HREF).equals(href) ? true : e.getAttribute(KW.HREF))));
                logger.info(String.format("isDisplayed - %s", e.isDisplayed()));
                logger.info(String.format("isEnabled - %s", e.isEnabled()));
                logger.info(String.format("!target:blank - %s", !e.getAttribute(KW.TARGET).contains(KW.BLANK)));
            }
            checkLinkThisTab(e, href);
        }

        public static void checkLinkThisTab(WebElement e, String href) {
            Assertions.mouseIsPointer(e);
            Assert.assertTrue(e.getAttribute(KW.HREF).equals(href) && e.isDisplayed()
                    && e.isEnabled() && !e.getAttribute(KW.TARGET).contains(KW.BLANK));
        }

        public static void checkLinkNewTab(WebElement e, String href) {
            Assertions.mouseIsPointer(e);
            Assert.assertTrue(e.getAttribute(KW.HREF).equals(href) && e.isDisplayed() && e.isEnabled()
                    && e.getAttribute(KW.TARGET).contains(KW.BLANK));
        }

        public static void checkLinkNewTab(WebElement e, String href, boolean debug) {
            if (debug) {
                logger.info(String.format("href - %s", (e.getAttribute(KW.HREF).equals(href) ? true : e.getAttribute(KW.HREF))));
                logger.info(String.format("isDisplayed - %s", e.isDisplayed()));
                logger.info(String.format("isEnabled - %s", e.isEnabled()));
                logger.info(String.format("!target:blank - %s", !e.getAttribute(KW.TARGET).contains(KW.BLANK)));
            }
            checkLinkNewTab(e, href);
        }

        public static void checkLinkThisTab(String path, String href) {
            WebElement e = driver().findElement(By.xpath(path));
            Assertions.mouseIsPointer(e);
            Assert.assertTrue(e.isDisplayed() && e.isEnabled());
            Assert.assertEquals(e.getAttribute(KW.HREF), href);
        }

        public static boolean checkLinkPart(WebElement e, String partOfHref) {
            Assertions.mouseIsPointer(e);
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

    public static void saveScreen(String fileName) {
        File srcFile = ((TakesScreenshot) driver()).getScreenshotAs(OutputType.FILE);
        String targetPath = PathStorage.SCREENSHOTS_DIR_PATH
                + FileSystems.getDefault().getSeparator() + fileName
                + com.hill.utilities.Dates.now().timeStamp()
                + ".png";
        logger.info(String.format("Saved screen: %s", targetPath));
        FileUtils.copy(srcFile.toPath(), Paths.get(targetPath));
    }

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
        logger.info("Take screenshot");
        return new ByteArrayInputStream(((TakesScreenshot) driver()).getScreenshotAs(OutputType.BYTES));
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
