package com.hill.mobile.screenobjects.elements;

import com.hill.mobile.utilities.Mobile;
import com.hill.mobile.utilities.TimeoutsMobile;
import io.appium.java_client.MobileElement;

/**
 * this class encapsulate logic of MobileElement wrapper
 * to make this logic reusable in each screen object
 * solves the problem of 'Can't locate an element by this strategy'
 */
public class Element {
    private final String locator;
    private MobileElement e;

    private Element(String locator) {
        this.locator = locator;
        e = null;
    }

    public static Element by(String locator) {
        return new Element(locator);
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

    public String locator() {
        return locator;
    }
}
