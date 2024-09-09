package com.hill.core;

import com.hill.utilities.constants.PathStorage;
import com.hill.utilities.constants.Timeouts;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WDManager {

    protected static final Logger logger = LoggerFactory.getLogger(WDManager.class);

    private static WebDriver driver;
    private static final Map<String, Object> preferences;

    static {
        preferences = new HashMap<>();
        preferences.put("profile.default_content_setting_values.notifications", 2);
        preferences.put("profile.default_content_settings.popups", 0);
        preferences.put("download.prompt_for_download", "false");
        preferences.put("download.default_directory", PathStorage.DOWNLOADS_PATH.toString());
        preferences.put("directory_upgrade", true);
        preferences.put("safebrowsing.enabled", "false");
    }

    public static WebDriver setDriver(String browserName) {
        switch (browserName.toLowerCase()) {

            case "chrome": {
                WebDriverManager.chromedriver()
                        .browserVersion("128.0.6613.119")
                        .setup();
                ChromeOptions chromeOptions = new ChromeOptions()
                        .setExperimentalOption("prefs", preferences)
                        .addArguments("--start-maximized")
                        .addArguments("--incognito")
                        .addArguments("--enable-strict-powerful-feature-restrictions")
                        .addArguments("--disable-geolocation")
                        .addArguments("--deny-permission-prompts");
                driver = new ChromeDriver(chromeOptions);
                break;
            }

            case "chrome-headless": {
                WebDriverManager.chromedriver()
                        .browserVersion("128.0.6613.119")
                        .setup();
                ChromeOptions chromeOptionsHl = new ChromeOptions()
                        .setExperimentalOption("prefs", preferences)
                        .addArguments("--proxy-server='direct://'")
                        .addArguments("--proxy-bypass-list=*")
                        .addArguments(String.format("--window-size=%s", PropertyReader.getProperty("screen.resolution")))
                        .addArguments("--headless")
                        .addArguments("--no-sandbox")
                        .addArguments("--disable-dev-shm-usage")
                        .addArguments("--enable-automation")
                        .addArguments("--disable-infobars")
                        .addArguments("--disable-browser-side-navigation")
                        .addArguments("--disable-gpu");
                driver = new ChromeDriver(chromeOptionsHl);
                break;
            }

            case "edge": {
                WebDriverManager.edgedriver()
                        .browserVersion("128.0.2739.67")
                        .setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setCapability("geo.enabled", false);
                driver = new EdgeDriver(edgeOptions);
                break;
            }

            case "firefox": {
                WebDriverManager.firefoxdriver()
                        .browserVersion("130.0") //todo move to test.properties
                        .setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setAcceptInsecureCerts(true)
                        .addPreference("dom.webnotifications.enabled", false)
                        .addPreference("dom.push.enabled", false)
                        .addPreference("geo.enabled", false)
                        .addPreference("geo.prompt.testing", false)
                        .addPreference("geo.prompt.testing.allow", false);
//                        .addArguments("--headless");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            }

            case "selenoid": {
                ChromeOptions options = new ChromeOptions(); //todo 3 times new ChromeOptions > to separate method
                options.setCapability("browserVersion", "127.0");

                Map<String, Object> selenoidOptions = new HashMap<>();
                selenoidOptions.put("name", "UI test session");
                selenoidOptions.put("sessionTimeout", "15m");
                selenoidOptions.put("env", Collections.singletonList("TZ=UTC"));
                Map<String, Object> labels = new HashMap<>();
                labels.put("manual", "true");
                selenoidOptions.put("labels", labels);
                selenoidOptions.put("enableVNC", true);
                selenoidOptions.put("enableVideo", true);

                options.setCapability("selenoid:options", selenoidOptions);
                options.addArguments("--window-size=1920,1080");

                try {
                    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
                } catch (MalformedURLException ignored) {
                    throw new RuntimeException("MalformedURLException");
                }
                break;
            }

            default:
                throw new IllegalArgumentException("Please specify valid browser name. Valid browser names are: firefox, chrome,chrome-headless, ie ,edge");

        }

        driver.manage().timeouts().implicitlyWait(Timeouts.AFTER_CLICK, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(Timeouts.PAGE_LOADING, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(Timeouts.JS_EXECUTION, TimeUnit.SECONDS);
        return driver;
    }

    public static void tearDown() {
        logger.debug("WDManager.tearDown()");
        driver.quit();
        driver = null;
    }

    public static WebDriver getDriver() {
        String browser = PropertyReader.getProperty("browser");
        if (driver == null) driver = setDriver(browser);
        return driver;
    }
}