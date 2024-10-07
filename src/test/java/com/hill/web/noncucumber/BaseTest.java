package com.hill.web.noncucumber;

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
import com.hill.web.utilities.scripts.ScriptRunnerWeb;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

public abstract class BaseTest {
    protected HomePage homePage = Factory.initPage(HomePage.class);
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected String preconditionDescription = "Home page is opened";
    protected Runnable preconditionAction = () -> {
    };
    private static boolean beforeAllAlreadyCalled = false;

    @BeforeSuite
    public void beforeAll() {
        logger.debug("@BeforeSuite.beforeAll()");
        if (!beforeAllAlreadyCalled) {
            FileUtils.cleanDir(PathStorage.SCREENSHOTS_DIR_PATH);
            FileUtils.cleanDir(PathStorage.RESULTS_DIR);
            FileUtils.cleanDir(PathStorage.REPORT_DIR);
            beforeAllAlreadyCalled = true;
        }
    }

    @AfterSuite
    public void afterAll() {
        logger.debug("@AfterSuite.afterAll().tearDown()");
        WDManager.tearDown();
        logger.info(ScriptRunnerWeb.generateAllureReport());
        logger.info(ScriptRunnerWeb.archiveReport());
    }

    @BeforeClass(alwaysRun = true)
    protected void beforeClass() {
        logger.debug("@BeforeClass.beforeClass().empty");
    }

    @AfterClass(alwaysRun = true)
    protected void afterClass() {
        logger.debug("@AfterClass.afterClass().empty");
    }

    @BeforeMethod(alwaysRun = true)
    protected void beforeMethod() {
        logger.debug("@BeforeMethod.beforeMethod()");
        Navigation.gotoPage(Pages.HOMEPAGE.link());
        Web.Wait.pageLoading();
        Factory.initPage(StartWarningWorkaround.class).reloadIfRequired();
        AddressFirstDialogWindow address = Factory.initPage(AddressFirstDialogWindow.class);
        CookiesDialogWindow cookies = Factory.initPage(CookiesDialogWindow.class);
        address.closeIfShown();
        cookies.acceptIfShown();
        Web.Wait.pageLoading();
        meetingPreconditions();
    }

    @AfterMethod
    protected void screenshotOnFail(ITestResult result) {
        logger.debug("@AfterMethod.screenshotOnFail()");
        if (!result.isSuccess()) {
            Allure.addAttachment("failed screen", Web.screenAsByteArray());
        }
    }


    @Step("Meeting precondition")
    protected void meetingPreconditions() {
        logger.info(String.format("Meeting precondition: %s", preconditionDescription));
        preconditionAction.run();
        Allure.addAttachment("Meeting precondition", Web.screenAsByteArray());
    }

}

