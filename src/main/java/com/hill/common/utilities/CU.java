package com.hill.common.utilities;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class CU {

    public static class Wait {
        public static void forSeconds(int seconds) {
            forMillis(seconds * 1000);
        }

        @SneakyThrows
        public static void forMillis(int millis) {
                TimeUnit.MILLISECONDS.sleep(millis);
        }
    }
}
