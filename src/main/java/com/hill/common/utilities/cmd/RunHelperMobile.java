package com.hill.common.utilities.cmd;

import java.util.stream.Collectors;

public class RunHelperMobile {

    public static boolean isEmulatorOk() {
        return !CommandRunner.runAdb(Commands.ADB.DEVICES).stream()
                .filter(s -> !(s.startsWith("List of devices") || s.isEmpty()))
                .collect(Collectors.toList()).isEmpty();
    }

    public static boolean isAppiumOk() {
        return Boolean.parseBoolean(CommandRunner.runBash(Commands.Mobile.APPIUM_STATUS).get(0).trim());
    }
}
