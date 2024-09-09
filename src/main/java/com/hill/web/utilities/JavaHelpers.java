package com.hill.web.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JavaHelpers {

    public static String getTimeStamp(String format) {
        /*
         * Example format are :
         * "yyyy MMM dd" for "2013 Nov 28"
         * "yyyyMMdd_HHmmss" for "20130131000000"
         * "yyyy MMM dd HH:mm:ss" for "2013 Jan 31 00:00:00"
         * "dd MMM yyyy" for "28 Nov 2017"
         */
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String timeStamp() {
        return getTimeStamp("_yyyyMMdd_HHmmss");
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

}