package com.hill.mobile.pageobjects;

import com.hill.mobile.utilities.Mobile;
import com.hill.mobile.utilities.TimeoutsMobile;
import io.appium.java_client.MobileElement;

public class ContactBookScreen extends BaseMobileScreen {

    private enum Els {
        CREATE_CONTACT_BUTTON("//android.widget.ImageButton[@content-desc='Create contact']"),
        EMPTY_STATE("com.google.android.contacts:id/empty_state_animation");

        private final String locator;
        private MobileElement e;

        Els(String locator) {
            this.locator = locator;
            e = null;
        }

        public MobileElement get() {
            if (e == null) {
                Mobile.Wait.until(String.format("Element '%s' not found", locator),
                        () -> Mobile.Is.displayed(locator),
                        TimeoutsMobile.ELEMENT_VISIBILITY);
                e = Mobile.Get.elementBy(locator);
            }
            return e;
        }

        public MobileElement set(MobileElement e) {
            this.e = e;
            return e;
        }

    }

    public CreateContactScreen createContact() {
        Els.CREATE_CONTACT_BUTTON.get().click();
        return currentPage(CreateContactScreen.class);
    }

    public boolean isEmpty() {
        return !Mobile.Get.elementsBy(Els.EMPTY_STATE.locator).isEmpty();
    }

}
