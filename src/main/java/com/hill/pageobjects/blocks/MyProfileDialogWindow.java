package com.hill.pageobjects.blocks;

import com.hill.pageobjects.BasePage;
import com.hill.utilities.Factory;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyProfileDialogWindow extends BasePage {
    private static final String CONTAINER = "//div[@class='d4013-a1 d4013-a9 d4013-b0 d4013-a2']";

    @FindBy(xpath = "(" + CONTAINER + "//button)[1]")
    WebElement loginOrRegistrationButton;

    @FindBy(xpath = "(" + CONTAINER + "//button)[2]")
    WebElement myCabinetButton;

    @Step("open login dialog")
    public AuthorizationDialogWindow toLogin() {
        click(loginOrRegistrationButton);
        addScreen();
        return Factory.initPage(AuthorizationDialogWindow.class);
    }
}
