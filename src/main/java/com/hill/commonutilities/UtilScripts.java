package com.hill.commonutilities;

import com.hill.mobile.utilities.bash.CommandRunner;
import com.hill.mobile.utilities.bash.Commands;

public class UtilScripts {
    public static void main(String[] args) throws InterruptedException {
//---------------------desktop
//        System.out.println("isWinDriverOk: " + RunHelperDesktop.isWinDriverOk());
//        System.out.println("isPort4723Free: " + RunHelperDesktop.isPort4723Free());
//        List<String> ll = CommandRunner.runComm(Commands.PowerShell.WIN_DRIVER_START);
//        ll.forEach(System.out::println);

//---------------------appium
//        CommandRunner.runBash(Commands.Bash.APPIUM_STOP);
//        CommandRunner.runBash(Commands.Bash.APPIUM_RUN);
//        System.out.println("isAppiumOk: " + RunHelperMobile.isAppiumOk());

//---------------------emulator
//        ScriptRunnerMobile.stopEmulators();
//        CommandRunner.runBash(Commands.Bash.EMULATOR_RUN);
//        System.out.println("isEmulatorOk: " + RunHelperMobile.isEmulatorOk());


//---------------------device
        CommandRunner.runAdb(Commands.ADB.HOME);


//---------------------Docker
//        CommandRunner.runComm(Commands.PowerShell.D_E_STOP);

//---------------------selenoid
//        System.out.println("isSelenoidOk: " + ScriptRunnerWeb.isSelenoidOk());
    }


}
