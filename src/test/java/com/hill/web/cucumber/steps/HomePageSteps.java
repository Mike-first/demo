package com.hill.web.cucumber.steps;

import com.hill.web.pageobjects.HomePage;
import com.hill.web.utilities.Factory;
import io.cucumber.java.en.When;


public class HomePageSteps extends BaseSteps {

    private final HomePage homePage;

    public HomePageSteps() {
        homePage = Factory.initPage(HomePage.class);
    }

    @When("the user search for {string} from home page")
    public void theUserSearchForSomethingFromHomePage(String keyword) {
        homePage.search(keyword);
    }

}
