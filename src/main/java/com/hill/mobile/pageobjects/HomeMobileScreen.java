package com.hill.mobile.pageobjects;

import com.hill.mobile.core.BaseMobileScreen;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;

public class HomeMobileScreen extends BaseMobileScreen {
    @FindBy(xpath = "//android.widget.TextView[@content-desc='Phone']")
    private MobileElement phoneButton;

    @FindBy(xpath = "//android.widget.TextView[@content-desc='Messages']")
    private MobileElement messagesButton;

    @FindBy(xpath = "//android.widget.TextView[@content-desc='Predicted app: Contacts']")
    private MobileElement contactsButton;

    @FindBy(xpath = "//android.widget.TextView[@content-desc='Chrome']")
    private MobileElement chromeButton;

    @FindBy(xpath = "//android.widget.TextView[@content-desc='Camera']")
    private MobileElement cameraButton;

    public ContactBookScreen contacts() {
        contactsButton.click();
        return currentPage(ContactBookScreen.class);
    }
}
