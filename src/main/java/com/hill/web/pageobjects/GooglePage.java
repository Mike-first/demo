package com.hill.web.pageobjects;

import com.hill.web.utilities.Web;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class GooglePage extends BasePage {
    @Step("open Google")
    public static GooglePage openGoogle() {
        Web.driver().get("https://www.google.com");
        Web.Wait.pageLoading();
        Web.addScreenshot();
        return new GooglePage();
    }

    @Step("search for {query}")
    public void searchFor(String query) {
        WebElement e = Web.driver().findElement(By.xpath("//textarea[@name='q']"));
        e.sendKeys(query);
        e.sendKeys(Keys.ENTER);
        Web.Wait.pageLoading();
        Web.addText("query", query);
        Web.addScreenshot();
    }
}
