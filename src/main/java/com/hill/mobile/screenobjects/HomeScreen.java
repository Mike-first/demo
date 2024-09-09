package com.hill.mobile.screenobjects;

import com.hill.mobile.screenobjects.elements.Element;

public class HomeScreen extends BaseMobileScreen {
    private enum Els {
        PHONE_BUTTON(Element.by("//android.widget.TextView[@content-desc='Phone']")),
        MESSAGES_BUTTON(Element.by("//android.widget.TextView[@content-desc='Messages']")),
        CONTACTS_BUTTON(Element.by("//android.widget.TextView[@content-desc='Predicted app: Contacts']")),
        CHROME_BUTTON(Element.by("//android.widget.TextView[@content-desc='Chrome']")),
        CAMERA_BUTTON(Element.by("//android.widget.TextView[@content-desc='Camera']"));
        private Element e;

        Els(Element e) {
            this.e = e;
        }
    }

    public ContactBookScreen contacts() {
        Els.CONTACTS_BUTTON.e.get().click();
        return currentPage(ContactBookScreen.class);
    }
}
