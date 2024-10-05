package com.hill.web.pageobjects.blocks;

import com.beust.jcommander.internal.Lists;
import com.hill.web.utilities.Web;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class PaginationBlock {
    private static final String CONTAINER = "//div[@class='q6e']";
    private static final List<String> NON_NUMBER_LINKS_TEXT = Lists.newArrayList("В начало", "...", "Дальше");

    @FindBy(xpath = CONTAINER + "//a")
    List<WebElement> links;

    public boolean isShown() {
        return Web.Is.elementDisplayed(CONTAINER);
    }

    public List<Integer> getAccessiblePagesNumbers() {
        return links.stream()
                .map(WebElement::getText)
                .filter(text -> !NON_NUMBER_LINKS_TEXT.contains(text))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public PaginationBlock next() {
        Web.Click.click(getLinkByText("Дальше"));
        Web.Wait.pageLoading();
        return this;
    }

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

    public int currentPage() {
        return links.stream()
                .filter(e -> e.getAttribute(Web.KW.CLASS).equals("eq5 qe5"))
                .map(WebElement::getText)
                .map(Integer::parseInt)
                .collect(Collectors.toList()).get(0);
    }

    public PaginationBlock byNumber(int number) {
        Web.Scroll.scrollForElementToBeUnderHeader(links.get(0));
        WebElement pageLink = getLinkByText(String.valueOf(number));
        Web.Click.click(pageLink);
        Web.Wait.pageLoading();
        return this;
    }

}
