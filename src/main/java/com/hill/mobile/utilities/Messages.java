package com.hill.mobile.utilities;

public interface Messages {
    static String noSuchElement(String locator) {
        return String.format("NoSuchElement '%s'.", locator);
    }

    static String notFoundDuring(String locator, int time) {
        return String.format("Element '%s' was not found during %d", locator, time);
    }
}
