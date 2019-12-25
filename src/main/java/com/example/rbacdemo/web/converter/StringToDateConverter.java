package com.example.rbacdemo.web.converter;

import com.example.rbacdemo.common.util.DateFormatUtils;
import com.example.rbacdemo.common.util.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String strDate) {
        if (StringUtils.isBlank(strDate)) return null;
        String datePattern;
        if (strDate.contains("-")) {
            if (strDate.contains(":")) {
                datePattern = DateFormatUtils.DATE_TIME_PATTERN;
            } else {
                datePattern = DateFormatUtils.DATE_PATTERN;
            }
        } else if (strDate.contains("/")) {
            if (strDate.contains(":")) {
                datePattern = DateFormatUtils.DATE_TIME_PATTERN_TWO;
            } else {
                datePattern = DateFormatUtils.DATE_PATTERN_TWO;
            }
        } else {
            throw new RuntimeException("日期格式有误");
        }
        Date date = DateFormatUtils.parse(strDate, datePattern);
        return date;
    }
}
