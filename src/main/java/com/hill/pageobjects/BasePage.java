package com.hill.pageobjects;

import com.hill.utilities.Web;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class BasePage {

    protected final Logger logger;

    protected BasePage() {
        PageFactory.initElements(driver(), this);
        logger = LoggerFactory.getLogger(this.getClass());
    }

    private static WebDriver driver() {
        return Web.driver();
    }

    public void click(WebElement element) {
        Web.Click.click(element);
    }

    public void addScreen() {
        Allure.addAttachment("screenshot", Web.screenAsByteArray());
    }

}

