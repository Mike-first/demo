package com.hill.web.utilities.constants;

public interface Messages {

    String INPUT_NOT_ENABLED = " Input not available for typing.";
    String ELEMENT_NOT_VISIBLE = " Element is not displayed.";

    String UTILITY_CLASS = "Utility class. Should not be instantiated.";

    static String noSuchElement(String xpath) {
        return String.format("NoSuchElement '%s'", xpath);
    }

}
