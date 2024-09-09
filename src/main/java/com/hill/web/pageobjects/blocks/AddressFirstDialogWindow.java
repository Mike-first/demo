package com.hill.web.pageobjects.blocks;

import com.hill.web.utilities.Web;
import com.hill.web.utilities.constants.Timeouts;

public class AddressFirstDialogWindow {
    private static final String TITLE = "//span[contains(text(),'Добавить адрес')]";
    private static final String CLOSE = "//div[contains(text(),'Не сейчас')]";

    public static void closeIfShown() {
        Web.addScreenshot();
        if (Web.Wait.waitNotStrict("AddressFirstDialog was not shown", AddressFirstDialogWindow::isShown, Timeouts.ELEMENT_VISIBILITY)) {
            Web.Click.blind(CLOSE,0,0);
        }
    }

    public static boolean isShown() {
        return !Web.Get.elements(TITLE).isEmpty();
    }

}
