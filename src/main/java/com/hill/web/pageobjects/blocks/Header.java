package com.hill.web.pageobjects.blocks;

import com.hill.web.pageobjects.BasePage;
import com.hill.web.utilities.Web;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Rectangle;

public class Header extends BasePage {

    private static final String CONTAINER = "//div[@class='dg7_9 d7g_9']";

    public static int getBottomPosition() {
        try {
            Rectangle header = Web.Element.el(CONTAINER).getRect();
            return header.height + header.y;
        } catch (NoSuchElementException e) {
            return 0;
        }
    }
}
