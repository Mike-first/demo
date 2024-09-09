package com.hill.utilities.constants;

import com.hill.core.PropertyReader;

public interface Timeouts {

    float FACTOR = Float.parseFloat(PropertyReader.getProperty("timeouts.factor"));

    int ELEMENT_VISIBILITY = (int) (15 * FACTOR);
    int CLICK = (int) (15 * FACTOR);
    int AFTER_CLICK = (int) (5 * FACTOR);
    int TEXT_INPUT = (int) (5 * FACTOR);
    int PROCESSING = (int) (25 * FACTOR);
    int ALERT = (int) (15 * FACTOR);
    int TEXT_TYPE = (int) (6 * FACTOR);
    int MODAL_TO_BE_SHOWN = (int) (7 * FACTOR);
    int DROPDOWN_TO_BE_OPENED = (int) (5 * FACTOR);
    int FILE_DOWNLOADING = (int) (45 * FACTOR);
    int PAGE_LOADING = (int) (30 * FACTOR);
    int JS_EXECUTION = (int) (15 * FACTOR);
    int CALENDAR = (int) (3 * FACTOR);
}
