package com.hill.web.pageobjects.blocks;

import com.hill.web.pageobjects.BasePage;
import com.hill.web.utilities.Web;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BuyAsCompanyDialogWindow extends BasePage {
    private static final String CONTAINER = "//div[@class='b6018-a5 b6018-a7']";

    @FindBy(xpath = CONTAINER + "//button[@class='cx6_9 ga113-a undefined']")
    WebElement closeButton;

    @FindBy(xpath = CONTAINER + "//button[@class='xc4_9 b2113-a0 b2113-b5 b2113-b1']")
    WebElement addCompanyButton;

    public void closeIfShown() {
        if (isShown()) {
            Web.Click.click(closeButton);
            Web.Wait.elementInvisibility(CONTAINER);
        }
    }

    public boolean isShown() {
        return Web.Is.elementDisplayed(CONTAINER);
    }
}
