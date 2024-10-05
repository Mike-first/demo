package com.hill.web.pageobjects.blocks;

import com.hill.web.pageobjects.BasePage;
import com.hill.web.utilities.Factory;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthorizationDialogWindow extends BasePage {
    private static final String CONTAINER = "//div[@data-widget='loginOrRegistration']";

    @FindBy(xpath = CONTAINER + "//input[@type='tel']")
    WebElement telInput;

    @FindBy(xpath = CONTAINER + "//button[@type='submit']")
    WebElement submitButton;

    @FindBy(xpath = CONTAINER + "//button[@class='hc3a_46 ga113-a']")
    WebElement loginWithMailButton;

    @Step("enter phone")
    public AuthorizationDialogWindow enterPhone(String phone) {
        telInput.sendKeys(phone); //Constants.VALID_PHONE
        addScreen();
        return this;
    }

    @Step("enter phone")
    public CheckPhoneDialogWindow submit() {
        click(submitButton);
        addScreen();
        return Factory.initPage(CheckPhoneDialogWindow.class);
    }

    @Step("enter phone")
    public CheckPhoneDialogWindow loginWithMail() {
        click(loginWithMailButton);
        addScreen();
        return Factory.initPage(CheckPhoneDialogWindow.class);
    }

}
