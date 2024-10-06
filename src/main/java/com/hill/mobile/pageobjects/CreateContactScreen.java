package com.hill.mobile.pageobjects;

import com.hill.mobile.pageobjects.elements.DropdownLabel;
import com.hill.mobile.utilities.Mobile;
import com.hill.mobile.utilities.TimeoutsMobile;
import io.appium.java_client.MobileElement;

public class CreateContactScreen extends BaseMobileScreen {
    private enum Els {
        DISCARD_BUTTON("//android.widget.ImageButton[@content-desc='Cancel']"),
        SAVE_BUTTON("//android.widget.Button[@resource-id='com.google.android.contacts:id/toolbar_button']"),
        FIRST_NAME_INPUT("//android.widget.EditText[@text='First name']"),
        EXPAND_NAME_BUTTON("com.google.android.contacts:id/expansion_view"),
        LASTNAME_INPUT("//android.widget.EditText[@text='Last name']"),
        COMPANY_INPUT("//android.widget.EditText[@text='Company']"),
        PHONE_INPUT("//android.widget.EditText[@text='Phone']"),
        LABEL_PHONE_DROPDOWN("//android.widget.Spinner[@content-desc='Mobile Phone']"),
        MAIL_INPUT("//android.widget.EditText[@text='Email']"),
        LABEL_MAIL_DROPDOWN("//android.widget.Spinner[@content-desc='Home Email']"),
        SIGNIFICANT_DATE_DROPDOWN("//android.widget.EditText[@text='Significant date']"),
        BIRTHDAY_DROPDOWN("//android.widget.Spinner[@content-desc='Birthday Special date']"),
        MORE_FIELDS_XPATH("//android.widget.TextView[@resource-id='com.google.android.contacts:id/more_fields']"),
        MORE_FIELDS("com.google.android.contacts:id/more_fields"),
        RELATED_PERSON_INPUT("//android.widget.EditText[@text='Related person']"),
        RELATIONSHIP_DROPDOWN("//android.widget.Spinner[@content-desc='Assistant Related person']"),
        SIP_INPUT("//android.widget.EditText[@text='SIP']"),
        NOTES_INPUT("//android.widget.EditText[@text='Notes']"),
        LABEL_DROPDOWN("//android.widget.EditText[@resource-id='com.google.android.contacts:id/group_list']"),
        DISCARD_CONFIRMATION_BUTTON("android:id/button2"),
        DISCARD_CANCEL_BUTTON("android:id/button1"),
        SCROLLER("com.google.android.contacts:id/contact_editor_scroller"),
        SCROLLER1("//android.widget.ScrollView[@resource-id='com.google.android.contacts:id/contact_editor_scroller']");

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

    public class Filler {

        private Filler() {
        }

        public Filler withFirstName(String firstName) {
            Els.FIRST_NAME_INPUT.get().sendKeys(firstName);
            return this;
        }

        public Filler withLastName(String lastName) {
            Els.LASTNAME_INPUT.get().sendKeys(lastName);
            return this;
        }

        public Filler withCompany(String company) {
            Els.COMPANY_INPUT.get().sendKeys(company);
            return this;
        }

        public Filler withPhone(String phone, String label) {
            Els.PHONE_INPUT.get().sendKeys(phone);
            DropdownLabel.byXpath(Els.LABEL_PHONE_DROPDOWN.locator).set(label);
            return this;
        }

        public Filler withMail(String mail, String label) {
            Els.MAIL_INPUT.get().sendKeys(mail);
            DropdownLabel.byXpath(Els.LABEL_MAIL_DROPDOWN.locator).set(label);
            return this;
        }

        public Filler withRelatedPerson(String person, String relationship) {
            Els.RELATED_PERSON_INPUT.get().sendKeys(person);
            DropdownLabel.byXpath(Els.RELATIONSHIP_DROPDOWN.locator).set(relationship);
            return this;
        }

        public CreateContactScreen build() {
            return CreateContactScreen.this;
        }
    }

    public ContactBookScreen save() {
        if (Els.DISCARD_CANCEL_BUTTON.get().isDisplayed()) Els.DISCARD_CANCEL_BUTTON.get().click();
        else Els.SAVE_BUTTON.get().click();
        return currentPage(ContactBookScreen.class);
    }

    public ContactBookScreen discard() {
        Els.DISCARD_BUTTON.get().click();
        MobileElement e = Els.DISCARD_CONFIRMATION_BUTTON.get();
        if (Mobile.Is.displayed(e)) e.click();
        return currentPage(ContactBookScreen.class);
    }

    public Filler feelInfo() {
        return new Filler();
    }

    public CreateContactScreen debug() {
        String s = DropdownLabel.byXpath(Els.LABEL_PHONE_DROPDOWN.locator).current();
        System.out.println(s);
        DropdownLabel dl = DropdownLabel.byLabel("Mobile").set("Home");

        DropdownLabel dl2 = dl.set("Work");
        System.out.println(dl2.current());

        DropdownLabel d3 = DropdownLabel.byLabel("Work").set("Other");
        System.out.println(d3.current());

        return this;
    }
}
