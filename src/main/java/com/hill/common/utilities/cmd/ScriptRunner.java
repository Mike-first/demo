package com.hill.common.utilities.cmd;

import com.hill.web.utilities.constants.PathStorage;

import java.util.Arrays;
import java.util.List;

public class ScriptRunner extends BaseRunner {
    private static List<String> runScript(String path, String... args) {
//        String name = scriptName(path);
        String[] command = new String[]{"sh", path};
        command = Arrays.copyOf(command, command.length + args.length);
        System.arraycopy(args, 0, command, 2, args.length);
        return runCmd(command);
    }

    //-----web-----
    public static String generateAllureReport() {
        return String.join("", runScript(scriptPath("generate_report"), PathStorage.ROOT));
    }

    public static String archiveReport() {
        String reportSrcPath = PathStorage.ALLURE_REPORT_DIR + "/index.html";
        return String.join("", runScript(scriptPath("archive_report"),
                reportSrcPath, PathStorage.REPORT_ARCHIVE_DIR));
    }

    public static boolean isSelenoidOk() {
        String s = String.join("", runScript(scriptPath("selenoid_status"), PathStorage.ROOT));
        return Boolean.parseBoolean(s.trim());
    }

    //-----mobile-----
    public static List<String> stopEmulators() {
        return runScript(scriptPath("stop_emulators"));
    }

    public static boolean appiumStatus() {
        String s = String.join("", runScript(scriptPath("appium_status")));
        return Boolean.parseBoolean(s.trim());
    }
}
