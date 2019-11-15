package com.example.rbacdemo.common.util;

public class StringUtils {
    public static boolean isBlank(String text) {
        return org.apache.commons.lang3.StringUtils.isBlank(text);
    }

    public static boolean isNotBlank(String text) {
        return !isBlank(text);
    }
}
