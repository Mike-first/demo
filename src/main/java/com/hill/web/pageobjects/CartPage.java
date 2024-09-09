package com.hill.web.pageobjects;


import com.hill.web.utilities.Web;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class CartPage extends BasePage {

    @FindBy(css = "[href='/cart']")
    WebElement widgetEmptyCart1;

    @FindBy(xpath = "//div[@data-widget='emptyCart']")
    WebElement widgetEmptyCart;


    public boolean isCartEmpty() {
        Web.Wait.pageLoading();
        return Web.Is.elementDisplayed(widgetEmptyCart);
    }

    @Step("mock method will wait for {seconds}")
    public CartPage mockMethod(int seconds) {
        Web.Wait.forSeconds(seconds);
        log.info("mockMethod, wait: " + seconds);
        return this;
    }

}
