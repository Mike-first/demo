package com.hill.pageobjects.blocks;

import com.hill.utilities.Web;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CookiesDialogWindow {
    private static final String CONTAINER = "//div[@data-widget='cookieBubble']";

    @FindBy(xpath = CONTAINER + "//button")
    WebElement okButton;

    public void acceptIfShown() {
        if (isShown()) {
            Web.Click.click(okButton);
            Web.Wait.elementInvisibility(CONTAINER);
        }
    }

    public boolean isShown() {
        return Web.Is.elementDisplayed(CONTAINER);
    }

}
