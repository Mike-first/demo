package com.hill.web.utilities.constants;

import com.hill.web.core.PropertiesWeb;

public interface Timeouts {
    float FACTOR = Float.parseFloat(PropertiesWeb.getProperty("timeouts.factor"));
    int ELEMENT_VISIBILITY = (int) (15 * FACTOR);
    int ELEMENT_SEARCH = (int) (15 * FACTOR);
    int CLICK = (int) (15 * FACTOR);
    int AFTER_CLICK = (int) (5 * FACTOR);
    int TEXT_INPUT = (int) (5 * FACTOR);
    int PROCESSING = (int) (25 * FACTOR);
    int ALERT = (int) (15 * FACTOR);
    int TEXT_TYPE = (int) (6 * FACTOR);
    int DIALOG_TO_BE_SHOWN = (int) (7 * FACTOR);
    int DROPDOWN_TO_BE_OPENED = (int) (5 * FACTOR);
    int FILE_DOWNLOADING = (int) (45 * FACTOR);
    int PAGE_LOADING = (int) (30 * FACTOR);
    int JS_EXECUTION = (int) (15 * FACTOR);
    int SELENOID_START = (int) (5 * FACTOR);
    int DOCKER_START = (int) (25 * FACTOR);
}
