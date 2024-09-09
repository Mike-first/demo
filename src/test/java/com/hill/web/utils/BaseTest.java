package com.hill.web.utils;

import com.hill.common.utilities.cmd.ScriptRunner;
import com.hill.web.core.Navigation;
import com.hill.web.core.Pages;
import com.hill.web.core.WDManager;
import com.hill.web.pageobjects.HomePage;
import com.hill.web.pageobjects.blocks.AddressFirstDialogWindow;
import com.hill.web.pageobjects.blocks.CookiesDialogWindow;
import com.hill.web.pageobjects.workarounds.StartWarningWorkaround;
import com.hill.web.utilities.Factory;
import com.hill.web.utilities.FileUtils;
import com.hill.web.utilities.Web;
import com.hill.web.utilities.constants.PathStorage;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

@Slf4j
public abstract class BaseTest {
    protected HomePage homePage = Factory.initPage(HomePage.class);
    protected String preconditionDescription = "Home page is opened";
    protected Runnable preconditionAction = () -> {
    };
    private static volatile boolean callOnce = false;

    @BeforeAll
    public static void beforeAll() {
        log.debug("BaseTest.@BeforeAll");
        if (!callOnce) {
            FileUtils.cleanDir(PathStorage.SCREENSHOTS_DIR);
            FileUtils.cleanDir(PathStorage.ALLURE_RESULTS_DIR);
            FileUtils.cleanDir(PathStorage.ALLURE_REPORT_DIR);
            callOnce = true;
        }
    }

    @AfterAll
    public static void afterAll() {
        log.debug("BaseTest.@AfterAll");
        WDManager.tearDown();
        log.info(ScriptRunner.generateAllureReport());
        log.info(ScriptRunner.archiveReport());
    }

//    @BeforeAll
//    public void beforeClass() {
//        log.debug("@BeforeAll.beforeClass().empty");
//    }
//
//    @AfterAll
//    public void afterClass() {
//        log.debug("@AfterAll.afterClass().empty");
//    }

    @BeforeEach
    public void beforeMethod() {
        log.debug("BaseTest.@Before");
        Navigation.gotoPage(Pages.HOMEPAGE.link());
        Web.Wait.pageLoading();
        StartWarningWorkaround.reloadIfRequired();
        AddressFirstDialogWindow.closeIfShown();
        CookiesDialogWindow.acceptIfShown();
        Web.Wait.pageLoading();
        meetingPreconditions();
    }

    @Step("Meeting precondition")
    public void meetingPreconditions() {
        log.info(String.format("Meeting precondition: %s", preconditionDescription));
        preconditionAction.run();
        Web.Wait.pageLoading();
        Web.addScreenshot();
    }

    @Step("mock method will wait for {seconds}")
    private static void mockMethod(int seconds) {
        Web.Wait.forSeconds(seconds);
        log.info("mockMethod, wait: " + seconds);
    }

    public static void waitStep(){
        int time = (int)(10 + 40 * Math.random());
        mockMethod(time);
    }
}

