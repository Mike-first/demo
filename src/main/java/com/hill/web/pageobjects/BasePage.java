package com.hill.web.pageobjects;

import com.hill.web.utilities.Web;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

@Slf4j
public abstract class BasePage {

    protected BasePage() {
        PageFactory.initElements(driver(), this);
    }

    private static WebDriver driver() {
        return Web.driver();
    }

    public void click(WebElement element) {
        Web.Click.click(element);
    }

    public void addScreen() {
        Web.addScreenshot();
    }

}

