package com.hill.common.utilities.cmd;

public interface Commands {

    interface PS {
        String WIN_DRIVER_STATUS = "(Get-Process | Where-Object { $_.ProcessName -Match 'WinAppDriver' }).Count -gt 0";
        String WIN_DRIVER_START = "Start-Process 'C:/Program Files (x86)/Windows Application Driver/WinAppDriver.exe'";
        String WIN_DRIVER_STOP = "Stop-Process -name WinAppDriver";
        String D_E_START = "Start-Process 'C:/Program Files/Docker/Docker/Docker Desktop.exe'";
        String D_E_STOP = "Get-Process | Where-Object { $_.Name -like '*docker*' } | Stop-Process";
        String D_E_STATUS = "docker info";
    }

    interface Docker {
        String D_PS = "docker ps";
        String D_PS_A = "docker ps -a";
        String D_IMAGES = "docker images";
        String D_STOP_ALL = "docker stop $(docker ps -aq)";
        String D_RM_ALL = "docker rm $(docker ps -aq)";
        String DC_PS_A = "docker-compose ps -a";
        String DC_PS = "docker-compose ps";
        String DC_UP_D = "docker-compose up -d";
        String DC_DOWN = "docker-compose down";
    }

    interface Mobile {
        String APPIUM_STATUS = "curl -s http://localhost:4723/status | grep -q 'server is ready' && echo 'true' || echo 'false'";
        String APPIUM_RUN = "nohup appium > %s/appiumLog.log 2>&1 &"; //String.format log/dir/path
        String APPIUM_STOP = "ps -ef | grep node | grep -v grep | awk '{print $2}' | xargs kill";
        String EMULATOR_RUN = "nohup emulator -avd MyEmulator -accel on > %s/myEmulLog.log 2>&1 &"; //String.format log/dir/path
        String NET_STAT_4723 = "netstat -ano | grep ':4723'";
    }

    interface ADB {
        String REBOOT = "reboot";
        String DEVICES = "devices";
        String POWER = shellInputKeyEvent(26);
        String VOLUME_UP = shellInputKeyEvent(24);
        String VOLUME_DOWN = shellInputKeyEvent(25);
        String HOME = shellInputKeyEvent(3);
        String BACK = shellInputKeyEvent(4);
        String APP_SWITCH = shellInputKeyEvent(187);
        String ENTER = shellInputKeyEvent(66);
        String DEL = shellInputKeyEvent(67);
        String SLEEP = shellInputKeyEvent(223);
        String WAKEUP = shellInputKeyEvent(224);

        static String shellInputKeyEvent(int code) {
            return String.format("shell input keyevent %d", code);
        }
    }
}
