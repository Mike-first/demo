package com.hill.api;

import com.hill.api.rest.TryApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


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
        Assertions.assertTrue(burnerTags.contains("туристическая"));
        Assertions.assertTrue(tentTags.contains("туристическая"));
    }
}
