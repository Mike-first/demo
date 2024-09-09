package com.hill.web;

import com.hill.web.pageobjects.blocks.MyProfileDialogWindow;
import com.hill.web.utils.BaseTest;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.SeverityLevel.CRITICAL;

public class AuthorizationTest extends BaseTest {
    MyProfileDialogWindow aw;

    {
        preconditionDescription = "Home page is opened, profile dialog is opened";
        preconditionAction = () -> {
            aw = homePage.toProfile();
        };
    }

    @Test
    @Description("authorization")
    @Severity(CRITICAL)
    @Issue("AUTH-12346")
    @Owner("Mike")
    @TmsLink("TMS-12346")
    @Epic("Web interface")
    @Feature("Essential features1")
    @Story("Authentication")
    public void authorizationTest(String param, boolean expected) {
        //won't implement. for demo of parameterized test run it's enough as is.
        System.out.printf("param: %s, expected: %s", param, expected);
    }


    static Object[][] primeNumbers() {
        return new Object[][]{
                {"9911213121", true},
                {"123", false}
        };
    }
}
