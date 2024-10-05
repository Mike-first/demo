package com.hill.web.core;

import com.hill.web.utilities.Web;
import com.hill.web.utilities.constants.Messages;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import static org.testng.Assert.assertTrue;

public class Assertions {

    private Assertions() {
        throw new IllegalStateException(Messages.UTILITY_CLASS);
    }

    @Step("Assert: element should have href value")
    public static void elementHaveHref(By locator, String hrefValue) {
        WebElement e = Web.driver().findElement(locator);
        Assert.assertEquals(e.getAttribute(Web.KW.HREF), hrefValue);
    }

    @Step("Assert: element should have text")
    public static void elementShouldHaveText(By locator, String text) {
        WebElement e = Web.driver().findElement(locator);
        Assert.assertEquals(e.getAttribute(Web.KW.INNER_TEXT), text);
    }

    @Step("Assert: viewport size")
    public static void assertViewportSize(String expected) {
        String js = "return ('('+Math.max(document.documentElement.clientWidth, window.innerWidth || 0) +', '" +
                "+ Math.max(document.documentElement.clientHeight, window.innerHeight || 0 ) +')');";
        Assert.assertEquals(Web.jse().executeScript(js), expected);
    }

    @Step("Assert: browser size")
    public static void assertBrowserSize(String expected) {
        Assert.assertEquals(Web.driver().manage().window().getSize().toString(), expected);
    }

    @Step("Assert: element is visible")
    public static void elementVisible(By locator) {
        Assert.assertTrue(Web.driver().findElement(locator).isDisplayed());
    }

    @Step("Assert: url is contains")
    public static void assertCurrentUrlContains(String urlContains) {
        String currentUrl = Web.driver().getCurrentUrl();
        assertTrue(currentUrl.contains(urlContains), currentUrl + " does not contains " + urlContains);
    }

    @Step("Assert: element is not visible")
    public static void elementNotVisible(By locator) {
        Assert.assertFalse(Web.driver().findElement(locator).isDisplayed());
    }

    @Step("Assert: mouse become a pointer after hovering")
    public static void mouseIsPointer(By locator) {
        WebElement e = Web.driver().findElement(locator);
        Web.Hover.overElement(e);
        Assert.assertTrue(e.getCssValue(Web.KW.CURSOR).equalsIgnoreCase(Web.KW.POINTER));
    }

    @Step("Assert: mouse becomes a pointer after hovering")
    public static void mouseIsPointer(WebElement e) {
        Web.Hover.overElement(e);
        Assert.assertTrue(e.getCssValue(Web.KW.CURSOR).equalsIgnoreCase(Web.KW.POINTER));
    }

    @Step("Assert: mouse not becomes a pointer after hovering")
    public static void mouseIsNotPointer(WebElement e) {
        Web.Hover.overElement(e);
        Assert.assertFalse(e.getCssValue(Web.KW.CURSOR).equalsIgnoreCase(Web.KW.POINTER));
    }
}
