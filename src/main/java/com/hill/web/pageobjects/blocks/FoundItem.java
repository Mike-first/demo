package com.hill.web.pageobjects.blocks;

import com.hill.web.pageobjects.BasePage;
import com.hill.web.utilities.StringUtils;
import com.hill.web.utilities.Web;
import org.openqa.selenium.WebElement;

public class FoundItem extends BasePage {
    private final WebElement CONTAINER;

    public FoundItem(WebElement container) {
        CONTAINER = container;
    }

    public String getTitle() {
        return Web.Get.elementUnder(CONTAINER, "//span[@class='tsBody500Medium']")
                .getAttribute(Web.KW.INNER_TEXT);
    }

    public String getLink() {
        return Web.Get.elementUnder(CONTAINER, "//span[@class='tsBody500Medium']//../../../a")
                .getAttribute(Web.KW.HREF);
    }

    public String getDescription() {
        return Web.Get.elementUnder(CONTAINER, "/span[@class='tsBody400Small']")
                .getAttribute(Web.KW.INNER_TEXT);
    }

    public int getPrice() {
        return Integer.parseInt(StringUtils.removeNonDigits(
                Web.Get.elementUnder(CONTAINER, "//span[@class='c3015-a1 tsHeadline500Medium c3015-c0']")
                        .getAttribute(Web.KW.INNER_TEXT)));
    }

}
