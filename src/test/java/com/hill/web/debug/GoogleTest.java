package com.hill.web.debug;

import com.hill.web.core.WDManager;
import com.hill.web.pageobjects.GooglePage;
import com.hill.web.utilities.Web;
import com.hill.web.utils.TestWatcherImpl;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.qameta.allure.Allure.step;

@Slf4j
@ExtendWith(TestWatcherImpl.class)
public class GoogleTest {
    private GooglePage googlePage;

    @BeforeEach
    public void setUp() {
        System.out.println("GoogleTest.@Before");
        Web.driver();
        Web.addText("test In Before", "test");

    }

    @AfterEach
    public void tearDown() {
        System.out.println("GoogleTest.@After");
        WDManager.tearDown();
    }


    @Severity(SeverityLevel.CRITICAL)
    @Description("google search test")
    @Owner("Mike")
    @Issue("AUTH-12345")
    @TmsLink("TMS-12345")
    @Link(name = "base url", url = "https://www.google.com")
    @Epic("Web interface")
    @Feature("Essential features")
    @Story("Web search")
    @Test
    public void testGoogleSearch() {
        googlePage = GooglePage.openGoogle();
        googlePage.searchFor("test");
        step("add a picture", Web::addPic);
        step("failed step", () -> {
            Assertions.fail();
        });

    }
}
