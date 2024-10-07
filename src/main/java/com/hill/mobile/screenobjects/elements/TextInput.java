package com.hill.mobile.screenobjects.elements;

import io.appium.java_client.MobileElement;

public class TextInput {
    //WIP
    //text inputs change selector, so if required to change or clear input
    // we need to know/remember current text
    // android.widget.EditText[@text="John"]
    // android.widget.EditText[@text='First name']

    private MobileElement input;
    private String xpath;
    private String currentText;

    public static TextInput byLabel(String label) {
        return new TextInput();
    }

    public static TextInput byXpath(String xpath) {
        return new TextInput();
    }

    public TextInput clear() {
        return this;
    }

    public TextInput send(String text) {
        return this;
    }

    public boolean isClean() {
        return false;
    }

    public boolean isContains(String text) {
        return false;
    }

    public TextInput enter() {
        return this;
    }
}
