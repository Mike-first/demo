package com.hill.apiTests;

import com.hill.api.TryApi;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ApiExampleTest {

    @Test()
    public void touristEquipmentIsPresentTest() {
        List<String> tentTags = TryApi.ozoneSearchTags("палатка");
        List<String> burnerTags = TryApi.ozoneSearchTags("горелка");
        System.out.println("tent tags:");
        tentTags.forEach(System.out::println);
        System.out.println("\nburner tags:");
        burnerTags.forEach(System.out::println);
        Assert.assertTrue(burnerTags.contains("туристическая"), "");
        Assert.assertTrue(tentTags.contains("туристическая"), "");
    }
}
