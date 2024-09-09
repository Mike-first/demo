package com.hill.core;

public enum Pages {
    HOMEPAGE(baseUrl()),
    CART(baseUrl() + "cart"),
    ORDERS(baseUrl() + "my/orderlist"),
    FAVORITES(baseUrl() + "my/favorites");

    private final String link;

    private static String baseUrl() {
        return PropertyReader.getProperty("base.url");
    }

    Pages(String link) {
        this.link = link;
    }

    public String link() {
        return link;
    }
}
