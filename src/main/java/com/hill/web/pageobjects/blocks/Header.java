package com.hill.web.pageobjects.blocks;

import com.hill.web.utilities.Web;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

public class Header {

    private static final String CONTAINER_TOP = "//div[@data-widget='topBar']";
    private static final String CONTAINER_HEADER = "//div[@id='stickyHeader']";
    private static final String CONTAINER_MENU = "//div[@data-widget='horizontalMenu']";

    public static int getBottomPosition() {
        try {
            WebElement bottom = Web.Is.elementDisplayed(CONTAINER_MENU) ? Web.Get.element(CONTAINER_MENU) : Web.Get.element(CONTAINER_HEADER);
            Rectangle header = bottom.getRect();
            return header.height + header.y + 20;
        } catch (NoSuchElementException e) {
            return 0;
        }
    }
}
