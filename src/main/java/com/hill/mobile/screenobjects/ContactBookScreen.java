package com.hill.mobile.screenobjects;

import com.hill.mobile.screenobjects.elements.Element;
import com.hill.mobile.utilities.Mobile;

public class ContactBookScreen extends BaseMobileScreen {

    private enum Els {
        CREATE_CONTACT_BUTTON(Element.by("//android.widget.ImageButton[@content-desc='Create contact']")),
        EMPTY_STATE(Element.by("com.google.android.contacts:id/empty_state_animation"));

        private Element e;

        Els(Element e) {
            this.e = e;
        }
    }

    public CreateContactScreen createContact() {
        Els.CREATE_CONTACT_BUTTON.e.get().click();
        return currentPage(CreateContactScreen.class);
    }

    public boolean isEmpty() {
        return !Mobile.Get.elementsBy(Els.EMPTY_STATE.e.locator()).isEmpty();
    }

}
