package com.hill.common.utilities.cmd;

import com.hill.web.utilities.constants.PathStorage;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class BaseRunner {
    protected static List<String> runCmd(String[] command) {
        int exitCode = -1;
        boolean isOk = true;
        List<String> results = new ArrayList<>();
        String cmd = String.join(" ", command);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            results = readOutput(process);
            exitCode = process.waitFor();
            isOk = exitCode == 0;
        } catch (IOException | InterruptedException e) {
            log.error(String.format("command '%s' was not executed successfully due to %s: %n%s", cmd, e.getClass().getSimpleName(), e));
            isOk = false;
        } finally {
            log.info(String.format("command '%s' was%s executed successfully", cmd, (isOk ? "" : " not")));
            if (!isOk) log.info(String.format("command '%s' exit code %d", cmd, exitCode));
        }
        return results;
    }

    private static List<String> readOutput(Process process) throws IOException {
        List<String> results = new ArrayList<>();
        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = input.readLine()) != null) {
                results.add(line);
            }
        }
        return results;
    }

//    protected static String scriptName(String path) {
//        return Arrays.stream(path.split("/"))
//                .filter(sss -> sss.contains(".sh"))
//                .collect(Collectors.toList()).get(0);
//    }

    protected static String scriptPath(String name) {
        return String.format(PathStorage.SCRIPT_BY_NAME, name);
    }
}