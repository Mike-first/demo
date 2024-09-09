package com.hill.common.utilities;

import com.hill.web.utilities.constants.Messages;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertyReaderBase {
    protected PropertyReaderBase() {
        throw new IllegalStateException(Messages.UTILITY_CLASS);
    }

    protected static Properties properties = null;

    //refactor if required to run web & mobile in bounds of one testrun
    protected static String getProperty(String key, Path propertyFile) {
        if (properties == null) properties = readPropertyFileOnce(propertyFile);
        return properties.getProperty(key);
    }

    private static Properties readPropertyFileOnce(Path propertyFile) {
        Properties properties = new Properties();
        try (InputStreamReader is = new InputStreamReader(
                Files.newInputStream(propertyFile), StandardCharsets.UTF_8
        )) {
            properties.load(is);
        } catch (IOException ignored) {
        }
        return properties;
    }
}
