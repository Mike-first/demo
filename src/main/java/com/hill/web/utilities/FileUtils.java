package com.hill.web.utilities;

import com.hill.web.core.PropertiesWeb;
import com.hill.web.utilities.constants.Messages;
import com.hill.web.utilities.constants.PathStorage;
import com.hill.web.utilities.constants.Timeouts;
import org.apache.commons.compress.archivers.zip.ZipFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.zip.ZipEntry;

public class FileUtils {

    public static final double DOWNLOADED_FILE_SIZE_ACCEPTABLE_DELTA = 0.01;

    private FileUtils() {
        throw new IllegalStateException(Messages.UTILITY_CLASS);
    }

    public static Map<String, Long> getZipContent(Path path) {
        Map<String, Long> files = new HashMap<>();
        Enumeration<? extends ZipEntry> entries = getZipEntities(path);
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            files.put(entry.getName(), entry.getSize());
        }
        return files;
    }

    private static Enumeration<? extends ZipEntry> getZipEntities(Path path) {
        try (ZipFile zip = new ZipFile(path.toFile())) {
            return zip.getEntries();
        } catch (IOException ignored) {
            throw new RuntimeException("IOException while reading zip");
        }
    }

    public static List<File> getDownloadsDirContent() {
        if (isDirNotEmpty(PathStorage.DOWNLOADS_PATH)) {
            File downloadsDir = PathStorage.DOWNLOADS_PATH.toFile();
            return Arrays.asList(downloadsDir.listFiles());
        }
        return Collections.emptyList();
    }

    public static boolean isDownloadsDirNotEmpty() {
        File dir = PathStorage.DOWNLOADS_PATH.toFile();
        return dir.listFiles() != null && dir.listFiles().length > 0;
    }

    public static boolean isSuchTypeFilePresent(String extension) {
        return getDownloadsDirContent().stream().anyMatch(f -> f.getName().endsWith(extension));
    }

    public static boolean waitUntilFileToBeDownloaded(String extension) {
        //todo move to Web
        int timeLeft = Timeouts.FILE_DOWNLOADING;
        int pollingEvery = 3;
        while (timeLeft > 0) {
            Web.Wait.forSeconds(pollingEvery);
            timeLeft -= pollingEvery;
            if (isSuchTypeFilePresent(extension)) {
                return true;
            }
        }
        return false;
    }

    public static float convertBytesIntoMb(long size) {
        return (float) size / 1024 / 1024;
    }

    public static boolean deleteFile(Path path) {
        return path.toFile().delete();
    }

    public static boolean cleanDownloads() {
        return cleanDir(PathStorage.DOWNLOADS_PATH);
    }

    public static void copy(Path original, Path target) {
        try {
            Files.copy(original, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignored) {
        }
    }

    public static boolean cleanDir(Path path) {
        if (!isDirNotEmpty(path)) return true;
        File dir = path.toFile();
        boolean result = true;
        for (File file : dir.listFiles()) {
            result &= file.delete();
        }
        return result;
    }

    public static boolean isDirNotEmpty(Path path) {
        File dir = path.toFile();
        return dir.listFiles() != null && dir.listFiles().length > 0;
    }

    public static String pathDownload() {
        //https://github.com/SeleniumHQ/selenium/issues/5292
        return Paths.get(PropertiesWeb.getProperty("tmp.dir")).toAbsolutePath().toString();
    }

    public static boolean isFileDownloaded(String fileName) {
        //https://stackoverflow.com/questions/30726126/detecting-a-file-downloaded-in-selenium-java
        File dir = new File(pathDownload());
        File[] dirContents = dir.listFiles();
        for (File dirContent : dirContents) {
            if (dirContent.getName().equals(fileName)) {
                dirContent.delete();
                return true;
            }
        }
        return false;
    }

    public static boolean cleanDir(String path) {
        if (!isDirNotEmpty(path)) return true;
        File dir = Paths.get(path).toAbsolutePath().toFile();
        boolean result = true;
        for (File file : dir.listFiles()) {
            result &= file.delete();
        }
        return result;
    }

    public static boolean isDirNotEmpty(String path) {
        File dir = Paths.get(path).toAbsolutePath().toFile();
        return dir.listFiles() != null && dir.listFiles().length > 0;
    }
}
