package com.hill.mobile;

import com.hill.mobile.pageobjects.HomeMobileScreen;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PhoneBookTest extends BaseMobileTest {
    @Test
    public void contactBookIsEmpty() {
        Assert.assertTrue(currentPage(HomeMobileScreen.class)
                        .contacts()
                        .isEmpty(),
                "contact book should be empty");
    }

    @Test
    public void contactCreated() {
        currentPage(HomeMobileScreen.class).contacts()
                .createContact()
                .feelInfo()
                .withFirstName("John")
                .withLastName("Doe")
                .withCompany("Anonimus Inc.")
                .withPhone("1111111111111", "")
                .build()
                .save();
    }
}
