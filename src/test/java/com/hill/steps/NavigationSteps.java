package com.hill.steps;

import com.hill.core.Navigation;
import com.hill.core.Pages;
import com.hill.pageobjects.blocks.AddressFirstDialogWindow;
import com.hill.pageobjects.blocks.CookiesDialogWindow;
import com.hill.pageobjects.workarounds.StartWarningWorkaround;
import com.hill.utilities.Factory;
import com.hill.utilities.Web;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class NavigationSteps extends BaseSteps {

    @Given("the user navigated to the site")
    public void theUserNavigatedToTheSite() {
        Navigation.gotoPage(Pages.HOMEPAGE.link());
        Web.Wait.pageLoading();
        Factory.initPage(StartWarningWorkaround.class).reloadIfRequired();
        AddressFirstDialogWindow address = Factory.initPage(AddressFirstDialogWindow.class);
        CookiesDialogWindow cookies = Factory.initPage(CookiesDialogWindow.class);
        address.closeIfShown();
        cookies.acceptIfShown();
        Web.Wait.pageLoading();
    }

    @And("^the user navigate to page with url \"([^\"]*)\"$")
    public void navigateToUrl(String pathAndQueryParams) {
        String url = Pages.HOMEPAGE.link() + pathAndQueryParams;
        Navigation.gotoPage(url);
        Web.Wait.pageLoading();
    }

}