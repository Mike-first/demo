package com.hill.web.cucumber.steps;

import com.hill.web.pageobjects.blocks.PaginationBlock;
import com.hill.web.utilities.Factory;
import com.hill.web.utilities.Web;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class PaginationSteps {

    private final PaginationBlock pb;
    private int pageToGo;

    public PaginationSteps() {
        this.pb = Factory.initPage(PaginationBlock.class);
    }


    @Then("pagination is shown on results page")
    public void paginationIsShownOnResultsPage() {
        Web.Scroll.toBottom();
        Assert.assertTrue(pb.isShown(), "pagination should be shown");

    }

    @And("the user click to {string}-th page")
    public void theUserClickToThPage(String page) {
        Web.Scroll.toBottom();
        pageToGo = pb.byNumber(Integer.parseInt(page))
                .currentPage();
    }

    @Then("current page number is {string}")
    public void currentPageNumberIs(String arg0) {
        int page = Integer.parseInt(arg0);
        Assert.assertEquals(pageToGo, page, String.format("current page should be %d", page));
    }
}
