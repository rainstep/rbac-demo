package com.example.rbacdemo.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JacksonUtils {
    private static Logger logger = LoggerFactory.getLogger(JacksonUtils.class);

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String stringify(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(dateFormat);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        String result = "";
        try {
            result = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error(ExceptionUtils
                    .getRootCauseMessage(e));
        }
        return result;
    }

    public static <T> T parse(String data, Class<T> type) {
        return parse(data, type, null, null);
    }

    public static <T> T parse(String data, JavaType type) {
        return parse(data, null, type, null);
    }

    public static <T> T parse(String data, TypeReference<T> type) {
        return parse(data, null, null, type);
    }

    private static <T> T parse(String data, Class<T> classType, JavaType javaType, TypeReference<T> typeReference) {
        T t = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(dateFormat);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            if (classType != null) {
                t = objectMapper.readValue(data, classType);
            } else if (javaType != null) {
                t = objectMapper.readValue(data, javaType);
            } else if (typeReference != null) {
                t = objectMapper.readValue(data, typeReference);
            }
        } catch (IOException e) {
            logger.error(ExceptionUtils.getRootCauseMessage(e));
        }
        return t;
    }
}
