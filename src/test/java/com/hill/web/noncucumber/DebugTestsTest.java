package com.hill.web.noncucumber;

import com.hill.web.utilities.Web;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DebugTestsTest extends BaseTest {

    @Features({
            @Feature("feature1"),
            @Feature("feature2")})
    @Stories({
            @Story(value = "Story 1"),
            @Story(value = "Story 2")
    })
    @Story("Story 1")
    @Feature("feature1")
    @TmsLink("test-1111")
    @Test(enabled = false)
    public void homePageIsLoadedTest() {
        Web.Wait.pageLoading();
        System.out.println(Web.driver().getTitle());
        Assert.assertEquals(Web.driver().getTitle(), "OZON маркетплейс – миллионы товаров по выгодным ценам");
    }

    @Test(enabled = false)
    public void homePageToCartTransitTest() {
        Assert.assertTrue(homePage.toCart().isCartEmpty(), "Cart should be empty");
    }

}
