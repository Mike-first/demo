package com.hill.web.pageobjects.blocks;

import com.hill.web.utilities.Web;
import com.hill.web.utilities.constants.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CookiesDialogWindow {
    private static final String CONTAINER = "//div[@data-widget='cookieBubble']";
    private static final String OK_BUTTON = CONTAINER + "//button";

    @FindBy(xpath = CONTAINER + "//button")
    WebElement okButton;

    public static void acceptIfShown() {
        Web.addScreenshot();
        if (Web.Wait.waitNotStrict("CookiesDialog was not shown", CookiesDialogWindow::isShown, Timeouts.ELEMENT_VISIBILITY)) {
            Web.Get.element(OK_BUTTON).click();
        }
    }

    public static boolean isShown() {
        return !Web.Get.elements(CONTAINER).isEmpty();
    }
}