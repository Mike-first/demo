package com.hill.common.utilities.cmd;

import java.util.List;

public class CommandRunner extends BaseRunner {
    private static final String BASH_EXE = "C:/Program Files/Git/bin/bash.exe";
    private static final String POWERSHELL_EXE = "powershell.exe";

    public static List<String> runBash(String cmd) {
        String[] fullCmd = {BASH_EXE, "-c", cmd};
        return runCmd(fullCmd);
    }

    public static List<String> runCurl(String cmd) {
        return runBash(cmd);
    }

    public static List<String> runPS(String cmd) {
        String[] fullCmd = {POWERSHELL_EXE, "-Command", cmd};
        return runCmd(fullCmd);
    }

        public static List<String> runAdb(String cmd) {
        String cmdPath = System.getenv().get("ANDROID_HOME").replace("\\", "/");
        String command = String.format("'%s/platform-tools/adb' %s", cmdPath, cmd);
        String[] fullCmd = {BASH_EXE, "-c", command};
        return runCmd(fullCmd);
    }
}
