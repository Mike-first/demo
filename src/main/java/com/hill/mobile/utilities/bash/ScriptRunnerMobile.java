package com.hill.mobile.utilities.bash;

import com.hill.commonutilities.ScriptRunnerBase;

import java.nio.file.Paths;

public class ScriptRunnerMobile extends ScriptRunnerBase {

    private static String scriptPath(String name) {
        return Paths.get(String.format("src/main/java/com/hill/mobile/utilities/bash/%s.sh", name))
                .toAbsolutePath().toString().replace("\\", "/").replace("D:", "/d");
    }

    public static String stopEmulators() {
        return runScript(scriptPath("stop_emulators"));
    }

    public static boolean appiumStatus() {
        //todo remove
        return Boolean.parseBoolean(runScript(scriptPath("appium_status")).trim());
    }
}
