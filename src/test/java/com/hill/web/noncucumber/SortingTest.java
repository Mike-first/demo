package com.hill.web.noncucumber;

import com.hill.web.pageobjects.SearchResultPage;
import com.hill.web.pageobjects.blocks.FoundItem;
import com.hill.web.utilities.Factory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.qameta.allure.SeverityLevel.NORMAL;

public class SortingTest extends BaseTest {
    SearchResultPage sr = Factory.initPage(SearchResultPage.class);

    {
        preconditionDescription = "Home page is opened, some item searched";
        preconditionAction = () -> {
            homePage.search("палатка");
        };
    }

    @Test(description = "Sort by price", groups = {"TEST-12345"})
    @Description("Sort by price1")
    @Severity(NORMAL)
    @Issue("AUTH-12345")
    @Owner("Mike")
    @TmsLink("TMS-12345")
    @Epic("Web interface")
    @Feature("Essential features")
    @Story("Authentication")
    public void sortingTest() {
        List<Integer> pr = sr
                .sortBy("Дешевле")
                .getFoundItems().stream()
                .map(FoundItem::getPrice)
                .collect(Collectors.toList());

        boolean isSortedByPriceLow = true;
        for (int i = 0; i < pr.size() - 1; i++) {
            isSortedByPriceLow &= pr.get(i) <= pr.get(i + 1);
        }
        Assert.assertTrue(isSortedByPriceLow, "Should be sorted by price low to high");
    }
}
