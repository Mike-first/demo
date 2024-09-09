package com.hill.web.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hill.common.utilities.CU;
import com.hill.common.utilities.cmd.CommandRunner;
import com.hill.common.utilities.cmd.Commands;
import com.hill.common.utilities.cmd.RunHelperWeb;
import com.hill.common.utilities.cmd.ScriptRunner;
import com.hill.web.utilities.constants.PathStorage;
import com.hill.web.utilities.constants.Timeouts;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WDManager {

    private static WebDriver driver;

    public static void tearDown() {
        log.debug("WDManager.tearDown()");
        driver.quit();
        driver = null;
    }

    public static WebDriver getDriver() {
        if (driver == null) driver = setTimeouts(setDriver());
        return driver;
    }

    private static WebDriver setTimeouts(WebDriver driver) {
        driver.manage().timeouts()
                .implicitlyWait(Timeouts.ELEMENT_SEARCH, TimeUnit.SECONDS)
                .pageLoadTimeout(Timeouts.PAGE_LOADING, TimeUnit.SECONDS)
                .setScriptTimeout(Timeouts.JS_EXECUTION, TimeUnit.SECONDS);
//        driver.manage().window().setSize(new Dimension(width(), height()));
        return driver;
    }

    private static WebDriver setDriver() {
        String browser = browserName().toLowerCase();
        if (runType().equals("selenoid")) {
            return createSelenoidDriver();
        } else {
            switch (browser) {
                case "chrome":
                    return createChromeDriver();
                case "edge":
                    return createEdgeDriver();
                case "firefox":
                    return createFirefoxDriver();
                case "opera":
                    return createOperaDriver();
                default:
                    throw new IllegalArgumentException("Invalid browser name: " + browser);
            }
        }
    }

    private static String browserName() {
        String bv = PropertiesWeb.getProperty("browser.ver");
        return bv.split(":")[0];
    }

    private static String browserVersion() {
        String bv = PropertiesWeb.getProperty("browser.ver");
        return bv.split(":")[1];
    }

    private static String runType() {
        String rt = PropertiesWeb.getProperty("run.type");
        return rt != null ? rt : "ui";
    }

    private static boolean isHeadless() {
        return runType().equals("headless");
    }

    private static int width() {
        return Integer.parseInt(PropertiesWeb.getProperty("window.size").split(",")[0]);
    }

    private static int height() {
        return Integer.parseInt(PropertiesWeb.getProperty("window.size").split(",")[1]);
    }

    private static Map<String, Object> chromePreferences() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.default_directory", PathStorage.DOWNLOADS_PATH.toString());
        prefs.put("download.directory_upgrade", true);
//        prefs.put("directory_upgrade", true);
        prefs.put("safebrowsing.enabled", false);
        return prefs;
    }

    private static ChromeOptions chromeOptions() {
        ChromeOptions options = new ChromeOptions()
                .setHeadless(isHeadless())
                .setAcceptInsecureCerts(true)
                .setExperimentalOption("prefs", chromePreferences())
                .addArguments("--enable-automation")
                .addArguments(String.format("--window-size=%s", PropertiesWeb.getProperty("window.size")))
                .addArguments("--incognito")
                .addArguments("--no-sandbox")
                .addArguments("--disable-dev-shm-usage")
                .addArguments("--enable-strict-powerful-feature-restrictions")
                .addArguments("--disable-geolocation")
                .addArguments("--deny-permission-prompts");

        if (PropertiesWeb.getProperty("proxy") != null) {
            options.addArguments(String.format("--proxy-server=%s", PropertiesWeb.getProperty("proxy")));
        }
        return options;
    }

    private static EdgeOptions edgeOptions() {
        EdgeOptions options = new EdgeOptions();
        if (isHeadless()) {
            options.setCapability("headless", true);
            options.setCapability("disable-gpu", true);
        }
        options.setCapability("width", width());
        options.setCapability("height", height());
        options.setCapability("window-size", PropertiesWeb.getProperty("window.size"));

        options.setCapability("acceptInsecureCerts", true);
        options.setCapability("geo.enabled", false);
        options.setCapability("inprivate", true);
        options.setCapability("download.default_directory", PathStorage.DOWNLOADS_PATH.toString());
        options.setCapability("window-size", PropertiesWeb.getProperty("window.size"));
        if (PropertiesWeb.getProperty("proxy") != null) {
            options.setCapability("proxy-server", PropertiesWeb.getProperty("proxy"));
        }
        return options;
    }

    private static FirefoxOptions firefoxOptions() {
        FirefoxOptions options = new FirefoxOptions()
                .setHeadless(isHeadless())
                .setAcceptInsecureCerts(true)
                .addArguments(String.format("--width=%s", width()))
                .addArguments(String.format("--height=%s", height()))
                .addPreference("dom.webnotifications.enabled", false)
                .addPreference("dom.push.enabled", false)
                .addPreference("geo.enabled", false)
                .addPreference("browser.download.folderList", 2)
                .addPreference("browser.download.dir", PathStorage.DOWNLOADS_PATH.toString())
                .addPreference("browser.helperApps.neverAsk.saveToDisk",
                        "application/pdf,application/zip,application/octet-stream,image/jpeg,image/png,text/plain,application/vnd.ms-excel")
                .addPreference("browser.download.useDownloadDir", true)
                .addPreference("geo.prompt.testing", false)
                .addPreference("geo.prompt.testing.allow", false);
        if (PropertiesWeb.getProperty("proxy") != null) {
            options.addArguments(String.format("--proxy-server=%s", PropertiesWeb.getProperty("proxy")));
        }
        return options;
    }

    private static OperaOptions operaOptions() {
        OperaOptions options = new OperaOptions();
        if (isHeadless()) {
            options.addArguments("--headless").addArguments("--disable-gpu");
        }

        options.addArguments("--incognito");
        options.setCapability("dom.webnotifications.enabled", false);
        options.setCapability("dom.push.enabled", false);
        options.setCapability("geo.enabled", false);
        options.setCapability("browser.download.folderList", 2);
        options.setCapability("browser.download.dir", PathStorage.DOWNLOADS_PATH.toString());
        options.setCapability("browser.helperApps.neverAsk.saveToDisk",
                "application/pdf,application/zip,application/octet-stream,image/jpeg,image/png,text/plain,application/vnd.ms-excel");
        options.setCapability("browser.download.useDownloadDir", true);
        options.setCapability("geo.prompt.testing", false);
        options.setCapability("geo.prompt.testing.allow", false);

//        options.setResolution(new Dimension(PropertiesWeb.getPropertyAsInt("window.width"), PropertiesWeb.getPropertyAsInt("window.height")));

        return options;
    }


    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().browserVersion(browserVersion()).setup();
        return new ChromeDriver(chromeOptions());
    }

    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().browserVersion(browserVersion()).setup();
        return new EdgeDriver(edgeOptions());
    }

    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().browserVersion(browserVersion()).setup();
        return new FirefoxDriver(firefoxOptions());
    }

    private static WebDriver createOperaDriver() {
        WebDriverManager.operadriver().browserVersion(browserVersion()).setup();
        return new OperaDriver(operaOptions());
    }


    private static WebDriver createSelenoidDriver() {
        String browser = browserName().toLowerCase();
        String version = shortVersion();
        validateBrowserVersion(browser, version);
        statusCheck();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserVersion", version);
        capabilities.setCapability("selenoid:options", selenoidOptions());

        switch (browser) {
            case "chrome":
                capabilities.merge(chromeOptions());
                break;
            case "firefox":
                capabilities.merge(firefoxOptions());
                break;
            case "edge":
                capabilities.merge(edgeOptions());
                break;
            case "opera":
                capabilities.merge(operaOptions());
                break;
            default:
                throw new IllegalArgumentException("Invalid browser name: " + browser);
        }

        try {
            return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("MalformedURLException", e);
        }
    }

    private static void statusCheck() {
        if (!RunHelperWeb.isDockerEnginOk()) {
            log.info("Starting Docker");
            CommandRunner.runPS(Commands.PS.D_E_START);
            CU.Wait.forSeconds(Timeouts.DOCKER_START);
        }
        if (RunHelperWeb.isDockerEnginOk()) log.info("Docker: Ok");
        else throw new RuntimeException("Docker starting failure");

        if (!ScriptRunner.isSelenoidOk()) {
            log.info("Starting Selenoid");
            CommandRunner.runBash(Commands.Docker.DC_UP_D);
            CU.Wait.forSeconds(Timeouts.SELENOID_START);
        }
        if (ScriptRunner.isSelenoidOk()) log.info("Selenoid: Ok");
        else throw new RuntimeException("Selenoid starting failure");
    }

    private static Map<String, Object> selenoidOptions() {
        Map<String, Object> options = new HashMap<>();
        Map<String, Object> labels = new HashMap<>();
        labels.put("manual", "true");

        options.put("name", "UI test session");
        options.put("sessionTimeout", "15m");
        options.put("env", Collections.singletonList("TZ=UTC"));
        options.put("labels", labels);
        options.put("enableVNC", true);
        options.put("enableVideo", true);
        return options;
    }

    public static String shortVersion() {
        String v = browserVersion();
        return v.split("\\.")[0].concat(".0");
    }

    public static void validateBrowserVersion(String browser, String version) {
        if (!selenoidVersions(browser).contains(version)) {
            throw new IllegalArgumentException("Requested version " + version + " is not available for " + browser);
        }
    }

    private static Set<String> selenoidVersions(String browser) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(Files.newInputStream(Paths.get("browsers.json")));
            JsonNode ver = rootNode.get(browser);
            Browser b = mapper.treeToValue(ver, Browser.class);
            return b.getVersions().keySet();
        } catch (IOException ignored) {
            return new HashSet<>();
        }
    }
}