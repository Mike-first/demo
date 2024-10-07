package com.hill.desktop.utilities;

import com.hill.mobile.utilities.bash.CommandRunner;
import com.hill.mobile.utilities.bash.Commands;

public class RunHelperDesktop {
    public static boolean isPort4723Free() {
        return CommandRunner.runBash(Commands.Bash.NET_STAT_4723).isEmpty();
    }

    public static boolean isWinDriverOk() {
        return Boolean.parseBoolean(CommandRunner.runComm(Commands.PowerShell.WIN_DRIVER_STATUS).get(0));
    }
}
