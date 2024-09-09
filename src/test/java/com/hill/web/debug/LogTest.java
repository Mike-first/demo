package com.hill.web.debug;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.impl.SimpleLogger;

@Slf4j(topic = "log_name")
public class LogTest {
    @Test
    public void homePageToCartTransitTest() {
        System.setProperty(SimpleLogger.LOG_FILE_KEY, "D:\\downloads\\logging\\simpleLog\\testRun.txt");
        System.setProperty("org.slf4j.simpleLogger.log.a.b.c", "DEBUG");//for nested
        Assertions.assertTrue(true);
        log.trace("trace");
        log.debug("test log debug");
        log.info("test log info");
        log.warn("test log warn");
        log.error("test log error");

        String s = log.getName();
        System.out.println("s = " + s);

    }
}
