package com.hill.web.utilities.constants;

import com.hill.web.core.PropertiesWeb;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class PathStorage {
    public static final String SCRIPTS_DIR = "src/main/java/com/hill/common/utilities/cmd/scripts/";

    public static final String SCRIPT_BY_NAME = String.join("",
            toLinuxDelimiter(toAbsPath(SCRIPTS_DIR).toString()), "/%s.sh");
    private PathStorage() {
        throw new RuntimeException(Messages.UTILITY_CLASS);
    }
    public static final String ROOT = toLinuxDelimiter(toAbsPath("src").getParent().toString());
    public static final Path SCREENSHOTS_DIR = toAbsPath("target/screenshots");
    public static final Path DOWNLOADS_PATH = toAbsPath(PropertiesWeb.getProperty("download.path"));
    public static final String ALLURE_RESULTS_DIR = toLinuxDelimiter(toAbsPath("target/allure-results").toString());
    public static final String ALLURE_REPORT_DIR = toLinuxDelimiter(toAbsPath("allure-report").toString());
    public static final String REPORT_ARCHIVE_DIR = PropertiesWeb.getProperty("archive.location");


    private static Path toAbsPath(String folder){
        return Paths.get(folder).toAbsolutePath();
    }

    private static String toLinuxDelimiter(String path) {
        return path.replace("\\", "/").replace("D:", "/d");
    }
}
