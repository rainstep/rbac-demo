package com.example.rbacdemo.common.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {
    private static Logger logger = LoggerFactory.getLogger(DateFormatUtils.class);

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_PATTERN_TWO = "yyyy/MM/dd HH:mm:ss";

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_PATTERN_TWO = "yyyy/MM/dd";

    public static final String DEFAULT_DATE_PATTERN = DATE_TIME_PATTERN;



    public static String format(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        return dateFormat.format(date);
    }

    public static String format(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static Date parse(String source, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(source);
        } catch (ParseException e) {
            logger.error(ExceptionUtils.getRootCauseMessage(e));
            return null;
        }
    }
}
