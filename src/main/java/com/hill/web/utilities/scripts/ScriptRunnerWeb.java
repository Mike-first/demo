package com.hill.web.utilities.scripts;

import com.hill.commonutilities.ScriptRunnerBase;
import com.hill.web.utilities.constants.PathStorage;

import java.nio.file.Paths;

public class ScriptRunnerWeb extends ScriptRunnerBase {

    private static String scriptPath(String name) {
        return Paths.get(String.format("src/main/java/com/hill/web/utilities/scripts/%s.sh", name))
                .toAbsolutePath().toString().replace("\\", "/").replace("D:", "/d");
    }

    public static String generateAllureReport() {
        String root = Paths.get("allure-report").toAbsolutePath().getParent().toString()
                .replace("\\", "/").replace("D:", "/d");
        return runScript(scriptPath("generate_report"), root);
    }

    public static String archiveReport() {
        String reportSrcPath = Paths.get("allure-report/index.html")
                .toAbsolutePath().toString().replace("\\", "/");
        return runScript(scriptPath("archive_report"),
                reportSrcPath, PathStorage.REPORT_ARCHIVE_DIR);
    }

    public static boolean isSelenoidOk() {
        String s = runScript(scriptPath("selenoid_status"), root());
        return Boolean.parseBoolean(s.trim());
    }

    private static String root() {
        return Paths.get("allure-report").toAbsolutePath().getParent().toString()
                .replace("\\", "/").replace("D:", "/d");
    }
}
