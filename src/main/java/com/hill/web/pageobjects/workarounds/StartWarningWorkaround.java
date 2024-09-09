package com.hill.web.pageobjects.workarounds;

import com.hill.web.utilities.Web;
import com.hill.web.utilities.constants.Timeouts;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StartWarningWorkaround {
    private static final String RELOAD = "//button[@id='reload-button']";
    private static final String WARNING = "//img[@alt='warning']";

    @FindBy(xpath = "//button[@id='reload-button']")
    WebElement reloadButton;

    @Step("workaround 'reload'")
    public static void reloadIfRequired() {
        Web.addScreenshot();
        if (Web.Wait.waitNotStrict("warning was not shown", StartWarningWorkaround::isShown, Timeouts.ELEMENT_VISIBILITY)) {
            Web.Scroll.toBottom();
            Web.Get.element(RELOAD).click();
            Web.Wait.pageLoading();
        }
    }

    public static boolean isShown() {
        return !Web.Get.elements(WARNING).isEmpty();
    }
}