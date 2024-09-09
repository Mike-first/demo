package com.hill.common.utilities.cmd;

import java.util.List;

public class RunHelperWeb {
    public static boolean isDockerEnginOk() {
        List<String> resp = CommandRunner.runPS(Commands.PS.D_E_STATUS);
        return !String.join("", resp).contains("ERROR: error during connect: Get");
    }
}
