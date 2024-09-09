package com.hill.web.pageobjects.blocks;


import com.google.common.collect.Lists;
import com.hill.web.utilities.Web;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class PaginationBlock {
    private static final String CONTAINER = "//div[@class='es3']";
    private static final List<String> NON_NUMBER_LINKS_TEXT = Lists.newArrayList("В начало", "...", "Дальше");

    @FindBy(xpath = CONTAINER + "//a")
    List<WebElement> links;

    public boolean isShown() {
        return Web.Is.elementDisplayed(CONTAINER);
    }

    @Step("get accessible pages numbers")
    public List<Integer> getAccessiblePagesNumbers() {
        return links.stream()
                .map(WebElement::getText)
                .filter(text -> !NON_NUMBER_LINKS_TEXT.contains(text))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    @Step("scroll for pagination to be under header")
    public PaginationBlock scrollTo(){
        Web.Scroll.toBottom();
        Web.Scroll.toView(thisBlock());
        Web.Scroll.forPixels(-Header.getBottomPosition());
        return this;
    }

    @Step("click next page")
    public PaginationBlock next() {
        Web.Click.click(getLinkByText("Дальше"));
        Web.Wait.pageLoading();
        return this;
    }

    @Step("go to first page")
    public PaginationBlock toBegin() {
        Web.Click.click(getLinkByText("В начало"));
        Web.Wait.pageLoading();
        return this;
    }

    private WebElement getLinkByText(String text) {
        return links.stream()
                .filter(e -> e.getAttribute(Web.KW.INNER_TEXT).contains(text))
                .collect(Collectors.toList()).get(0);
    }

    @Step("get current Page")
    public int currentPage() {
        Web.addScreenshot();
        return links.stream()
                .filter(e -> e.getCssValue("background").startsWith("rgb(0, 91, 255)"))
                .map(WebElement::getText)
                .map(Integer::parseInt)
                .collect(Collectors.toList())
                .get(0);
    }

    @Step("go to page# ${number}")
    public PaginationBlock byNumber(int number) {
        WebElement pageLink = getLinkByText(String.valueOf(number));
        Web.Click.click(pageLink);
        Web.Wait.pageLoading();
        return this;
    }

    public WebElement thisBlock(){
        return Web.Get.element(CONTAINER);
    }

}
