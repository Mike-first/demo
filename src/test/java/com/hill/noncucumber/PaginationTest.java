package com.hill.noncucumber;

import com.hill.pageobjects.blocks.PaginationBlock;
import com.hill.utilities.Factory;
import com.hill.utilities.Web;
import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PaginationTest extends BaseTest {

    PaginationBlock pb = Factory.initPage(PaginationBlock.class);

    {
        preconditionDescription = "Home page is opened, some item searched";
        preconditionAction = () -> {
            homePage.search("палатка");
        };
    }

    @Test()
    public void paginationTest() {
        Allure.addAttachment("screenshot", Web.screenAsByteArray());
        Web.Scroll.toBottom();
        Assert.assertTrue(pb.isShown(), "Pagination should be shown");
        int index = Web.randomIndex(pb.getAccessiblePagesNumbers(), 2);
        int newPage = pb.byNumber(index)
                .currentPage();
        Assert.assertEquals(newPage, index, String.format("current page should be %d", index));

    }

}
