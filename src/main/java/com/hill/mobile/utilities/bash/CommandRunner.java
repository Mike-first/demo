package com.hill.mobile.utilities.bash;

import com.hill.web.utilities.Web;
import com.hill.web.utilities.constants.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CommandRunner {
    protected static final Logger logger = LoggerFactory.getLogger(CommandRunner.class);

    private CommandRunner() {
        throw new IllegalStateException(Messages.UTILITY_CLASS);
    }

    private static void runBash(String cmd) {
        int exitCode = -1;
        boolean isOk = true;

        String bashPath = "C:/Program Files/Git/bin/bash.exe";
        String cmdPath = System.getenv().get("ANDROID_HOME").replace("\\", "/");
        String command = String.format("'%s/platform-tools/adb' %s", cmdPath, cmd);
        String[] fullCmd = {bashPath, "-c", command};

        try {
            Process process = Runtime.getRuntime().exec(fullCmd);
            exitCode = process.waitFor();
            isOk = exitCode == 0;
        } catch (IOException | InterruptedException e) {
            logger.info(String.format("command '%s' was not executed successfully due to %s: %n%s", cmd, e.getClass().getSimpleName(), e));
            isOk = false;
        } finally {
            logger.info(String.format("command '%s' was%s executed successfully", cmd, (isOk ? "" : " not")));
            if (!isOk) logger.info(String.format("command '%s' exit code %d", cmd, exitCode));
        }
    }

    private static String shellInputKeyEvent(int code) {
        return String.format("shell input keyevent %d", code);
    }

    public static void runEventCmd(String cmd) {
        switch (cmd) {
            case "home":
                runBash(shellInputKeyEvent(3));
                break;

            case "back":
                runBash(shellInputKeyEvent(4));
                break;

            case "reboot":
                runBash("reboot");
                Web.Wait.forSeconds(45); //todo mv to timeouts
                break;

            default:
                throw new IllegalArgumentException("Bad command");

        }
    }

}
