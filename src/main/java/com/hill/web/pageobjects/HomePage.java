package com.hill.web.pageobjects;


import com.hill.web.pageobjects.blocks.MyProfileDialogWindow;
import com.hill.web.utilities.Factory;
import com.hill.web.utilities.Web;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.nio.charset.StandardCharsets;

public class HomePage extends BasePage {

    //todo header as separate block

    @FindBy(xpath = "//div[@data-widget='profileMenuAnonymous']")
    WebElement profileMenu;

    @FindBy(css = "[href='/my/orderlist']")
    WebElement ordersButton;

    @FindBy(css = "[href='/my/favorites']")
    WebElement favoritesButton;

    @FindBy(css = "[href='/cart']")
    WebElement cartButton;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement searchButton;

    @FindBy(xpath = "//form[@action='/search']//input")
    WebElement searchInput;

    @Step("search for items")
    public SearchResultPage search(String s) {
        Allure.attachment("search request", s);
        searchInput.sendKeys(s);
        click(searchButton);
        Web.Wait.pageLoading();
        Allure.addAttachment("search_result", Web.screenAsByteArray());

        return Factory.initPage(SearchResultPage.class);
    }

    @Step("open cart")
    public CartPage toCart() {
        Web.Wait.pageLoading();
//        click(cartButton);
//        String[] tabs = Web.Tab.openNewTab(cartButton);
//        Web.Tab.switchToNewWindowPage();
        Web.Wait.pageLoading();
        addScreen();
        return Factory.initPage(CartPage.class);
    }

    @Step("open favorites")
    public FavoritesPage toFavorites() {
        Web.Wait.pageLoading();
        click(favoritesButton);
        Web.Wait.pageLoading();
        addScreen();
        return Factory.initPage(FavoritesPage.class);
    }

    @Step("open orders")
    public OrdersPage toOrders() {
        Web.Wait.pageLoading();
        click(ordersButton);
        Web.Wait.pageLoading();
        addScreen();
        return Factory.initPage(OrdersPage.class);
    }

    @Step("to profile")
    public MyProfileDialogWindow toProfile() {
        Web.Wait.pageLoading();
        Web.Hover.overElement(profileMenu);

        addScreen();
        return Factory.initPage(MyProfileDialogWindow.class);
    }

    @Attachment(value = "Annotated attachment [{type}]", type = "text/plain", fileExtension = ".txt")
    public byte[] textAttachment(String type, String content) {
        return content.getBytes(StandardCharsets.UTF_8);
    }

}
