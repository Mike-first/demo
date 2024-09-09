package com.hill.web;

import com.hill.web.utilities.Web;
import com.hill.web.utils.BaseTest;
import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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
    @Test
    @Disabled
    public void homePageIsLoadedTest() {
        Web.Wait.pageLoading();
        System.out.println(Web.driver().getTitle());
        Assertions.assertEquals(Web.driver().getTitle(), "OZON маркетплейс – миллионы товаров по выгодным ценам");
    }

    @Test
    @Disabled("temporary due to some problem")
    public void homePageToCartTransitTest() {
        Assertions.assertTrue(homePage.toCart().isCartEmpty(), "Cart should be empty");
    }

}
