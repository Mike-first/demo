package com.hill.web;


import com.hill.web.utils.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CartTest extends BaseTest {


    @Test
    @Disabled("reason")
    public void mockTest() {
        Assertions.fail("failed mock test");
    }

    @Test
    @Disabled("reason")
    public void mockTest2() {
        System.out.println("successful mock test");
    }

}
