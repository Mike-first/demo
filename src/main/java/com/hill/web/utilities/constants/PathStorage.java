package com.hill.web.utilities.constants;

import com.hill.web.core.PropertiesWeb;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class PathStorage {
    private PathStorage() {
        throw new RuntimeException(Messages.UTILITY_CLASS);
    }

    public static final Path SCREENSHOTS_DIR_PATH = Paths.get("target/screenshots").toAbsolutePath();
    public static final Path DOWNLOADS_PATH = Paths.get(PropertiesWeb.getProperty("download.path")).toAbsolutePath();
    public static final Path RESULTS_DIR = Paths.get("target/allure-results").toAbsolutePath();
    public static final Path REPORT_DIR = Paths.get("allure-report").toAbsolutePath();
    public static final String REPORT_ARCHIVE_DIR = PropertiesWeb.getProperty("archive.location")
            .replace("\\", "/");

}
