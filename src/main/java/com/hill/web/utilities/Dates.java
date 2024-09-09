package com.hill.web.utilities;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Dates {

    private LocalDateTime localDateTime;

    private static final String DEFAULT_PATTERN = "_yyyy.MM.dd_HH.mm.ss";

    private Dates(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public static Dates now() {
        return new Dates(LocalDateTime.now());
    }

    public Dates offset(String dir, int count, ChronoUnit chronoUnit) {
        if (dir.equals("plus")) {
            localDateTime = localDateTime.plus(count, chronoUnit);
        } else if (dir.equals("minus")) {
            localDateTime = localDateTime.minus(count, chronoUnit);
        }
        return this;
    }

    public String timeStamp() {
        DateFormat dateFormat = new SimpleDateFormat(DEFAULT_PATTERN);
        return dateFormat.format(Timestamp.valueOf(localDateTime));
    }

    public String timeStamp(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(Timestamp.valueOf(localDateTime));
    }

    public static String getCurrentTimeStamp(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }
}
