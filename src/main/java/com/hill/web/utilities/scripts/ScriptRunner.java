package com.hill.web.utilities.scripts;

import com.beust.jcommander.internal.Lists;
import com.hill.web.utilities.constants.Messages;
import com.hill.web.utilities.constants.PathStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class ScriptRunner {

    protected static final Logger logger = LoggerFactory.getLogger(ScriptRunner.class);

    public static String generateAllureReport() {
        String root = Paths.get("allure-report").toAbsolutePath().getParent().toString()
                .replace("\\", "/").replace("D:", "/d");
        return runScript("generate_report", root);
    }

    private static String runScript(String name, String... args) {

        String path = scriptPath(name);
        boolean isOk = true;
        String result = null;
        int exitCode = -1;

        ProcessBuilder builder = new ProcessBuilder("sh", path);
        builder.command().addAll(Lists.newArrayList(args));

        try {
            Process process = builder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                exitCode = process.waitFor();
                result = output.toString();
                isOk = exitCode == 0;
            }
        } catch (IOException | InterruptedException e) {
            logger.error(String.format("script '%s' was not executed successfully due to %s: %n%s", name, e.getClass().getSimpleName(), e));
            isOk = false;
        } finally {
            logger.info(String.format("script '%s' was%s executed successfully", name, (isOk ? "" : " not")));
            if (!isOk) logger.info(String.format("script '%s' exit code %d", name, exitCode));
        }

        return result;
    }

    private static String scriptPath(String name) {
        return Paths.get(String.format("src/main/java/com/hill/web/utilities/scripts/%s.sh", name))
                .toAbsolutePath().toString().replace("\\", "/");
    }

    public static String helloWorld() {
        return runScript("hello", "World");
    }

    private ScriptRunner() {
        throw new IllegalStateException(Messages.UTILITY_CLASS);
    }

    public static String archiveReport() {
        String reportSrcPath = Paths.get("allure-report/index.html")
                .toAbsolutePath().toString().replace("\\", "/");
        return runScript("archive_report",
                reportSrcPath, PathStorage.REPORT_ARCHIVE_DIR);
    }
}
