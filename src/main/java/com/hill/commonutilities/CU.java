package com.hill.commonutilities;

public class CU {

    public static class Wait {
        public static void forSeconds(int seconds) {
            forMillis(seconds * 1000);
        }

        public static void forMillis(int millis) {
            try {
                Thread.sleep(millis);
                //TimeUnit.MILLISECONDS.sleep(millis);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
