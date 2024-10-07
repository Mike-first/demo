package com.hill.mobile.utilities.bash;

import com.hill.web.utilities.constants.Messages;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CommandRunner {
    //    protected static final Logger log = LoggerFactory.getLogger(CommandRunner.class);
    private static final String BASH_EXE = "C:/Program Files/Git/bin/bash.exe";

    private CommandRunner() {
        throw new IllegalStateException(Messages.UTILITY_CLASS);
    }

    public static List<String> runAdb(String cmd) {
        String cmdPath = System.getenv().get("ANDROID_HOME").replace("\\", "/");
        String command = String.format("'%s/platform-tools/adb' %s", cmdPath, cmd);
        String[] fullCmd = {BASH_EXE, "-c", command};
        return runComm(fullCmd);
    }

    private static String shellInputKeyEvent(int code) {
        return String.format("shell input keyevent %d", code);
    }

    public static List<String> runBash(String cmd) {
        String[] fullCmd = {BASH_EXE, "-c", cmd};
        return runComm(fullCmd);
    }

    public static List<String> runComm(String[] fullCmd) {
        int exitCode = -1;
        boolean isOk = true;
        List<String> results = new ArrayList<>();
        String cmd = String.join(" ", fullCmd);
        try {
            Process process = Runtime.getRuntime().exec(fullCmd);
            try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = input.readLine()) != null) {
                    results.add(line);
                }
                exitCode = process.waitFor();
                isOk = exitCode == 0;
            }
        } catch (IOException | InterruptedException e) {
            log.info(String.format("command '%s' was not executed successfully due to %s: %n%s", cmd, e.getClass().getSimpleName(), e));
            isOk = false;
        } finally {
            log.info(String.format("command '%s' was%s executed successfully", cmd, (isOk ? "" : " not")));
            if (!isOk) log.info(String.format("command '%s' exit code %d", cmd, exitCode));
        }
        return results;
    }
}
