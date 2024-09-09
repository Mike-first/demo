package com.hill.web.pageobjects;

import com.hill.web.pageobjects.blocks.FoundItem;
import com.hill.web.utilities.Web;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchResultPage extends BasePage {

    private static final String SEARCH_RESULTS_CONTAINER = "//div[@data-widget='searchResultsV2']";
    private static final String SORTING_CONTAINER = "//div[@data-popper-placement='bottom-start']";

    @FindBy(xpath = "(//div[@data-widget='tagList'])[1]//a")
    WebElement tagList;

    @FindBy(xpath = "//div[@data-widget='searchResultsSort']//input")
    WebElement sortInput;

    @FindBy(xpath = SORTING_CONTAINER + "//div[contains(@class,'tsCompact500Medium')]")
    List<WebElement> sortOptions;

    @FindBy(xpath = SEARCH_RESULTS_CONTAINER + "//div[@class='jn_23 nj_23']")
    List<WebElement> searchResultItems;

    @Step("sort search results")
    public SearchResultPage sortBy(String option) {
//        Web.Wait.pageLoading();
        openSorting();
        WebElement opt = sortOptions.stream().filter(o -> o.getText().equals(option)).collect(Collectors.toList()).get(0);
        click(opt);
        Web.Wait.pageLoading();
        Web.addScreenshot();
        return this;
    }

    @Step("open sorting")
    public SearchResultPage openSorting() {
        Web.Wait.pageLoading();
        if (!Web.Is.elementDisplayed(SORTING_CONTAINER)) click(sortInput);
        Web.Wait.elementVisibility(SORTING_CONTAINER);
        return this;
    }

    @Step("close sorting")
    public SearchResultPage closeSorting() {
        Web.Wait.pageLoading();
        if (Web.Is.elementDisplayed(SORTING_CONTAINER)) click(sortInput);
        Web.Wait.elementInvisibility(SORTING_CONTAINER);
        return this;
    }

    @Step("collect found items list")
    public List<FoundItem> getFoundItems() {
        List<FoundItem> res = new ArrayList<>();
        for (WebElement e : searchResultItems) {
            res.add(new FoundItem(e));
        }
        return res;
    }

}
