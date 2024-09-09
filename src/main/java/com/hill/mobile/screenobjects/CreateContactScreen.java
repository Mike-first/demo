package com.hill.mobile.screenobjects;

import com.hill.mobile.screenobjects.elements.DropdownLabel;
import com.hill.mobile.screenobjects.elements.Element;
import com.hill.mobile.utilities.Mobile;
import io.appium.java_client.MobileElement;

public class CreateContactScreen extends BaseMobileScreen {
    private enum Els {
        DISCARD_BUTTON(Element.by("//android.widget.ImageButton[@content-desc='Cancel']")),
        SAVE_BUTTON(Element.by("//android.widget.Button[@resource-id='com.google.android.contacts:id/toolbar_button']")),
        FIRST_NAME_INPUT(Element.by("//android.widget.EditText[@text='First name']")),
        EXPAND_NAME_BUTTON(Element.by("com.google.android.contacts:id/expansion_view")),
        LASTNAME_INPUT(Element.by("//android.widget.EditText[@text='Last name']")),
        COMPANY_INPUT(Element.by("//android.widget.EditText[@text='Company']")),
        PHONE_INPUT(Element.by("//android.widget.EditText[@text='Phone']")),
        LABEL_PHONE_DROPDOWN(Element.by("//android.widget.Spinner[@content-desc='Mobile Phone']")),
        MAIL_INPUT(Element.by("//android.widget.EditText[@text='Email']")),
        LABEL_MAIL_DROPDOWN(Element.by("//android.widget.Spinner[@content-desc='Home Email']")),
        SIGNIFICANT_DATE_DROPDOWN(Element.by("//android.widget.EditText[@text='Significant date']")),
        BIRTHDAY_DROPDOWN(Element.by("//android.widget.Spinner[@content-desc='Birthday Special date']")),
        MORE_FIELDS_XPATH(Element.by("//android.widget.TextView[@resource-id='com.google.android.contacts:id/more_fields']")),
        MORE_FIELDS(Element.by("com.google.android.contacts:id/more_fields")),
        RELATED_PERSON_INPUT(Element.by("//android.widget.EditText[@text='Related person']")),
        RELATIONSHIP_DROPDOWN(Element.by("//android.widget.Spinner[@content-desc='Assistant Related person']")),
        SIP_INPUT(Element.by("//android.widget.EditText[@text='SIP']")),
        NOTES_INPUT(Element.by("//android.widget.EditText[@text='Notes']")),
        LABEL_DROPDOWN(Element.by("//android.widget.EditText[@resource-id='com.google.android.contacts:id/group_list']")),
        DISCARD_CONFIRMATION_BUTTON(Element.by("android:id/button2")),
        DISCARD_CANCEL_BUTTON(Element.by("android:id/button1")),
        SCROLLER(Element.by("com.google.android.contacts:id/contact_editor_scroller")),
        SCROLLER1(Element.by("//android.widget.ScrollView[@resource-id='com.google.android.contacts:id/contact_editor_scroller']"));

        private final Element e;

        Els(Element e) {
            this.e = e;
        }
    }

    public class Filler {

        private Filler() {
        }

        public Filler withFirstName(String firstName) {
            Els.FIRST_NAME_INPUT.e.get().sendKeys(firstName);
            return this;
        }

        public Filler withLastName(String lastName) {
            Els.LASTNAME_INPUT.e.get().sendKeys(lastName);
            return this;
        }

        public Filler withCompany(String company) {
            Els.COMPANY_INPUT.e.get().sendKeys(company);
            return this;
        }

        public Filler withPhone(String phone, String label) {
            Els.PHONE_INPUT.e.get().sendKeys(phone);
            DropdownLabel.byXpath(Els.LABEL_PHONE_DROPDOWN.e.locator()).set(label);
            return this;
        }

        public Filler withMail(String mail, String label) {
            Els.MAIL_INPUT.e.get().sendKeys(mail);
            DropdownLabel.byXpath(Els.LABEL_MAIL_DROPDOWN.e.locator()).set(label);
            return this;
        }

        public Filler withRelatedPerson(String person, String relationship) {
            Els.RELATED_PERSON_INPUT.e.get().sendKeys(person);
            DropdownLabel.byXpath(Els.RELATIONSHIP_DROPDOWN.e.locator()).set(relationship);
            return this;
        }

        public CreateContactScreen build() {
            return CreateContactScreen.this;
        }
    }

    public ContactBookScreen save() {
        if (Els.DISCARD_CANCEL_BUTTON.e.get().isDisplayed()) Els.DISCARD_CANCEL_BUTTON.e.get().click();
        else Els.SAVE_BUTTON.e.get().click();
        return currentPage(ContactBookScreen.class);
    }

    public ContactBookScreen discard() {
        Els.DISCARD_BUTTON.e.get().click();
        MobileElement e = Els.DISCARD_CONFIRMATION_BUTTON.e.get();
        if (Mobile.Is.displayed(e)) e.click();
        return currentPage(ContactBookScreen.class);
    }

    public Filler feelInfo() {
        return new Filler();
    }

    public CreateContactScreen debug() {
        String s = DropdownLabel.byXpath(Els.LABEL_PHONE_DROPDOWN.e.locator()).current();
        System.out.println(s);
        DropdownLabel dl = DropdownLabel.byLabel("Mobile").set("Home");

        DropdownLabel dl2 = dl.set("Work");
        System.out.println(dl2.current());

        DropdownLabel d3 = DropdownLabel.byLabel("Work").set("Other");
        System.out.println(d3.current());

        return this;
    }
}
