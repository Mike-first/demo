package com.hill.mobile.screenobjects;

import com.hill.mobile.core.MDManager;
import com.hill.mobile.core.PropertiesMobile;
import com.hill.mobile.utilities.Mobile;
import com.hill.mobile.utilities.ScreenFactory;
import com.hill.mobile.utilities.TimeoutsMobile;

public class BaseMobileScreen {
    private ScreenFactory factory = new ScreenFactory(MDManager.getDriver());

    protected boolean isDebug() {
        return Boolean.getBoolean(PropertiesMobile.getProperty("debug.mod"));
    }

    protected <T> T currentPage(Class<T> clazz) {
        Mobile.Wait.until("",
                this::isLoaded,
                TimeoutsMobile.SCREEN_LOADING);
        return factory.init(clazz);
    }

    protected boolean isLoaded() {
        //override in problematic screens
        return true;
    }
}
