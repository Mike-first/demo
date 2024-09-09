package com.hill.web.core;

import com.hill.web.utilities.Web;
import io.qameta.allure.Step;

public class Navigation {

    @Step("open link")
    public static void gotoPage(String link) {
        Web.driver().get(link);
    }

}
