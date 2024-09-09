package com.hill.mobile.screenobjects.elements;

import com.hill.mobile.utilities.Mobile;
import com.hill.mobile.utilities.TimeoutsMobile;
import io.appium.java_client.MobileElement;

public class DropdownLabel {
    public DropdownLabel(String xpath) {
        this.label = labelOfXpath(xpath);
        Mobile.Wait.until(String.format("dropdown label '%s'", label),
                () -> Mobile.Is.displayed(xpath),
                TimeoutsMobile.ELEMENT_VISIBILITY);
        this.input = Mobile.Get.elementBy(xpath);
//        this.expButton = Mobile.Get.byXpathUnderElement(input, EXPAND); //todo
    }

    private MobileElement input;
    private MobileElement expButton;
    private String label;

    private static final String XPATH = "//android.widget.Spinner[@content-desc='%s Phone']";
    private static final String EXPAND = "//android.widget.ImageButton[@content-desc='Show dropdown menu']";

    public static DropdownLabel byLabel(String label) {
        return byXpath(String.format(XPATH, label));
    }

    public static DropdownLabel byXpath(String xpath) {
        return new DropdownLabel(xpath);
    }

    public String current() {
        return label;
    }

    public DropdownLabel set(String label) {
        Mobile.Scroll.slide(-200);
        input.click();
        //fixme no ideas how to get locator for dropdown options
        //blind click works
        Mobile.acts().moveToElement(input)
                .moveByOffset(0, -800)
                .click().build().perform();

        return this;
    }

    private static String labelOfXpath(String xpath) {
        return (xpath.contains("android.widget.Spinner"))
                ? xpath.substring(xpath.indexOf("=") + 2, xpath.indexOf(" "))
                : xpath;
    }
}
