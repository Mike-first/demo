package com.hill.mobile.core;

import com.hill.common.utilities.PropertyReaderBase;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PropertiesMobile extends PropertyReaderBase {

    protected PropertiesMobile() {
        super();
    }

    private static final Path PROPERTY_FILE = Paths.get("src/main/resources/mobiletest.properties").toAbsolutePath();

    public static String getProperty(String key) {
        return getProperty(key, PROPERTY_FILE);
    }

}
