package com.hill.steps;

import com.hill.core.WDManager;
import com.hill.utilities.FileUtils;
import com.hill.utilities.Web;
import com.hill.utilities.constants.PathStorage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Hooks {
    private static boolean beforeAllAlreadyCalled = false;
    private static Collection<String> prevTags = Collections.emptyList();
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private static int scenarioOrdinal = 0;

    @Before
    public static void beforeAll() {
        if (!beforeAllAlreadyCalled) {
            FileUtils.cleanDir(PathStorage.SCREENSHOTS_DIR_PATH);
            FileUtils.cleanDir(PathStorage.RESULTS_DIR);
            beforeAllAlreadyCalled = true;
        }
    }

    @Before
    public static void beforeScenario(Scenario scenario) {
        scenarioOrdinal++;
        Web.driver();
        logger.info(String.format("\n############################################### " +
                "Scenario '%s' is running (#%d):", scenario.getName(), scenarioOrdinal));
    }

    @Before
    public static void beforeTag(Scenario scenario) {
        if (getCurrentTags(scenario).contains("@download_documents")) {
            FileUtils.cleanDownloads();
        }

        if (getCurrentTags(scenario).containsAll(Arrays.asList("@download_documents"))) {
            FileUtils.cleanDownloads();
//            WebUtils.refreshBrowser();
        }
    }

    @Before
    public static void beforeFeature(Scenario scenario) {
        if (!prevTags.containsAll(getCurrentTags(scenario))) {
            prevTags = getCurrentTags(scenario);
            Web.Tab.refreshBrowser();
        }
    }

    private static Collection<String> getCurrentTags(Scenario scenario) {
        return scenario.getSourceTagNames();
    }

    @After
    public static void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            Web.saveScreen("scenario '" + scenario.getName().replace("\"", "'") + "' failed");
            logger.info(String.format("URL where failed: %s", Web.driver().getCurrentUrl()));
            final String[] tags = new String[]{"failed scenario tags: "};
            getCurrentTags(scenario).forEach(tag -> tags[0] = tags[0].concat(tag + " "));
            logger.info(tags[0]);
        } else {
            logger.info(String.format("Scenario '%s' is passed successfully.%n", scenario.getName()));
        }
    }

    @After
    public void tearDown() {
        WDManager.tearDown();
    }
}