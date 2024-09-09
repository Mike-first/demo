package com.hill.mobile;

import com.hill.mobile.screenobjects.HomeScreen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PhoneBookTest extends BaseMobileTest {
    @Test
    public void contactBookIsEmpty() {
        Assertions.assertTrue(currentPage(HomeScreen.class)
                        .contacts()
                        .isEmpty(),
                "contact book should be empty");
    }

    @Test
    public void contactCreated() {
        currentPage(HomeScreen.class).contacts()
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
