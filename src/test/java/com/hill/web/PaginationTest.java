package com.hill.web;

import com.hill.web.pageobjects.blocks.PaginationBlock;
import com.hill.web.utilities.Factory;
import com.hill.web.utilities.Web;
import com.hill.web.utils.BaseTest;
import com.hill.web.utils.TestWatcherImpl;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(TestWatcherImpl.class)
public class PaginationTest extends BaseTest {

    PaginationBlock pb = Factory.initPage(PaginationBlock.class);

    {
        preconditionDescription = "Home page is opened, some item searched";
        preconditionAction = () -> {
            homePage.search("палатка");
        };
    }

    @Test()
    @Description("Going through pages")
    public void paginationTest() {
        Assertions.assertTrue(pb.scrollTo().scrollTo().isShown(), "Pagination should be shown");
        int index = Web.randomIndex(pb.getAccessiblePagesNumbers(), 2);
        int newPage = pb
                .byNumber(index)
                .scrollTo().scrollTo()
                .currentPage();
        Assertions.assertEquals(newPage, index, String.format("current page should be %d", index));
    }
}
