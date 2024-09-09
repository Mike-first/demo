package com.hill.web.utils;

import com.hill.web.utilities.Web;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

@Slf4j
public class TestWatcherImpl implements TestWatcher {

    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        System.out.println("TestWatcherImpl.testDisabled");
        log.info(String.format("test %s disabled due to reason %s", context.getDisplayName(), reason.orElse("reason not specified")));
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("TestWatcherImpl.testSuccessful");
        log.info(String.format("test %s passed", context.getDisplayName()));
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        System.out.println("TestWatcherImpl.testAborted");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.println("TestWatcherImpl.testFailed");
        Web.addScreenshot();
        log.info(String.format("test %s failed due to reason %s", context.getDisplayName(), cause.toString()));
    }

}
