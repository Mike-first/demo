package com.hill.common.utilities.cmd;

public class RunHelperDesktop {
    public static boolean isPort4723Free() {
        return CommandRunner.runBash(Commands.Mobile.NET_STAT_4723).isEmpty();
    }

    public static boolean isWinDriverOk() {
        return Boolean.parseBoolean(CommandRunner.runPS(Commands.PS.WIN_DRIVER_STATUS).get(0));
    }
}
