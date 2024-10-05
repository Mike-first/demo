package com.hill.web.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String removeNonDigits(String input) {
        Pattern pattern = Pattern.compile("\\D+");
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }
}
