package com.hill.pageobjects;


import com.hill.utilities.Web;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {

    @FindBy(css = "[href='/cart']")
    WebElement widgetEmptyCart1;

    @FindBy(xpath = "//div[@data-widget='emptyCart']")
    WebElement widgetEmptyCart;


    public boolean isCartEmpty() {
        Web.Wait.pageLoading();
        return Web.Is.elementDisplayed(widgetEmptyCart);
    }

}
