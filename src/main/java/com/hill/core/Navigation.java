package com.hill.core;

import com.hill.utilities.Web;
import io.qameta.allure.Step;

public class Navigation {

    @Step("open link")
    public static void gotoPage(String link) {
        Web.driver().get(link);
    }

}
