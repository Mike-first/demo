package com.hill.web.noncucumber;

import com.hill.web.pageobjects.blocks.MyProfileDialogWindow;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.CRITICAL;

public class AuthorizationTest extends BaseTest {
    MyProfileDialogWindow aw;

    {
        preconditionDescription = "Home page is opened, profile dialog is opened";
        preconditionAction = () -> {
            aw = homePage.toProfile();
        };
    }

    @Test(description = "authorization", groups = {"TEST-12346"}, dataProvider = "credentials")
    @Description("authorization")
    @Severity(CRITICAL)
    @Issue("AUTH-12346")
    @Owner("Mike")
    @TmsLink("TMS-12346")
    @Epic("Web interface")
    @Feature("Essential features1")
    @Story("Authentication")
//    @Parameters("params")
    public void authorizationTest(@Optional String params) {
        //won't implement. for demo of parameterized test run it's enough as is.
        System.out.println(params);
        System.out.println();
    }

    @DataProvider(name = "credentials")
    public static Object[][] primeNumbers() {
        return new Object[][]{{"9911213121", true}, {"123", false}};
    }
}
