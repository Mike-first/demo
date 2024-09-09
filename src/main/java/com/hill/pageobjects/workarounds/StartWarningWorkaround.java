package com.hill.pageobjects.workarounds;

import com.hill.pageobjects.BasePage;
import com.hill.utilities.Web;
import com.hill.utilities.constants.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StartWarningWorkaround extends BasePage {

    @FindBy(xpath = "//img[@alt='warning']")
    WebElement warningImg;

    @FindBy(css = "#reload-button")
    WebElement reloadButton;

    public void reloadIfRequired() {
        Web.Wait.waitNotStrict("", () -> Web.Is.elementDisplayed(warningImg), Timeouts.ELEMENT_VISIBILITY);
        if (Web.Is.elementDisplayed(warningImg)) {
            Web.Scroll.toBottom();
            Web.Click.click(reloadButton);
            Web.Wait.pageLoading();
        }
    }

}
