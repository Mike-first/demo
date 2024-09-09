package com.hill.web.utilities;

import com.hill.web.core.WDManager;
import com.hill.web.utilities.constants.Messages;
import org.openqa.selenium.support.PageFactory;

public class Factory {
    private Factory() {
        throw new IllegalStateException(Messages.UTILITY_CLASS);
    }

    public static <T> T initPage(Class<T> page) {
        return PageFactory.initElements(WDManager.getDriver(), page);
    }
}
