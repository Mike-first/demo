package com.hill.steps;

import com.hill.pageobjects.HomePage;
import com.hill.utilities.Factory;
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
