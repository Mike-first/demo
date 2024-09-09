package com.hill.web;

import com.hill.web.pageobjects.SearchResultPage;
import com.hill.web.pageobjects.blocks.FoundItem;
import com.hill.web.utilities.Factory;
import com.hill.web.utils.BaseTest;
import com.hill.web.utils.TestWatcherImpl;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(TestWatcherImpl.class)
public class SortingTest extends BaseTest {
    SearchResultPage sr = Factory.initPage(SearchResultPage.class);

    {
        preconditionDescription = "Home page is opened, some item searched";
        preconditionAction = () -> {
            homePage.search("палатка");
        };
    }

    @Test
    @Description("Sort by price1")
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
        Assertions.assertTrue(isSortedByPriceLow, "Should be sorted by price low to high");
    }
}
