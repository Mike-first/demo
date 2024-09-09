package com.hill.noncucumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {


    @Test()
    public void mockTest() {
        Assert.fail("failed mock test");
    }

    @Test()
    public void mockTest2() {
        System.out.println("successful mock test");
    }

}
