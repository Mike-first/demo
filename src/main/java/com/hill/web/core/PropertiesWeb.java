package com.hill.web.core;

import com.hill.commonutilities.PropertyReaderBase;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PropertiesWeb extends PropertyReaderBase {
    private PropertiesWeb() {
        super();
    }

    private static final Path PROPERTY_FILE = Paths.get("src/main/resources/webtest.properties").toAbsolutePath();

    public static String getProperty(String key) {
        return getProperty(key, PROPERTY_FILE);
    }
}
