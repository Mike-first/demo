package com.hill.pageobjects.blocks;

import com.hill.pageobjects.BasePage;
import com.hill.utilities.Web;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddressFirstDialogWindow extends BasePage {
    private static final String CONTAINER = "//div[@class='d4013-a4']";

    @FindBy(xpath = CONTAINER + "//button[@class='a2013-a4']")
    WebElement closeButton;

    @FindBy(xpath = CONTAINER + "//div[contains(text(),'Добавить')]/../../.")
    WebElement addButton;

    @FindBy(xpath = CONTAINER + "//div[contains(text(),'Не сейчас')]/../../.")
    WebElement notNowButton;

    @FindBy(xpath = CONTAINER + "//div[contains(text(),'Сменить')]/../../.")
    WebElement changeButton;

    @FindBy(xpath = CONTAINER + "//div[contains(text(),'Пропустить')]/../../.")
    WebElement skipButton;

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
