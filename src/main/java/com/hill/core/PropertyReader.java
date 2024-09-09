package com.hill.core;

import com.hill.utilities.constants.Messages;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertyReader {
    private PropertyReader() {
        throw new IllegalStateException(Messages.UTILITY_CLASS);
    }

    private static final Path PROPERTY_FILE = Paths.get("src/main/resources/test.properties").toAbsolutePath();
    private static Properties properties = null;

    public static String getProperty(String key) {
        if (properties == null) properties = readPropertyFileOnce();
        return properties.getProperty(key);
    }

    private static Properties readPropertyFileOnce() {
        Properties properties = new Properties();
        try (InputStreamReader is = new InputStreamReader(
                Files.newInputStream(PROPERTY_FILE), StandardCharsets.UTF_8
        )) {
            properties.load(is);
        } catch (IOException ignored) {
        }
        return properties;
    }
}
