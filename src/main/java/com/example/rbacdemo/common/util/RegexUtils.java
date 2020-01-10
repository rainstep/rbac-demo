package com.example.rbacdemo.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class RegexUtils {
    private static Logger logger = LoggerFactory.getLogger(RegexUtils.class);

    /**
     * 手机
     */
    public static final String MOBILE_REGEX = "^1[3-9]\\d{9}$";

    /**
     * 邮箱
     */
    public static final String EMAIL_REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 账号以字母、下划线开头，只允许字母、数字和下划线，最少5位长度。
     */
    public static final String ACCOUNT_REGEX = "^[A-Za-z_]+\\w{4,}";

    /**
     * 密码只允许字母、数字和下划线，最少6位长度。
     */
    public static final String PASSWORD_REGEX = "\\w{6,}";

    /** 中文 */
    public static final String CHINESE_REGEX = "[\\u4e00-\\u9fa5]+";



    public static boolean checkAccount(String account) {
        boolean result = isMatch(ACCOUNT_REGEX, account);
        if (!result) logger.error("账号格式错误：{}", account);
        return result;
    }

    public static boolean checkMobile(String mobile) {
        boolean result = isMatch(MOBILE_REGEX, mobile);
        if (!result) logger.error("手机格式错误：{}", mobile);
        return result;
    }

    public static boolean checkEmail(String email) {
        boolean result = isMatch(EMAIL_REGEX, email);
        if (!result) logger.error("邮箱格式错误：{}", email);
        return result;
    }

    public static boolean checkPassword(String password) {
        boolean result = isMatch(PASSWORD_REGEX, password);
        if (!result) logger.error("密码格式错误：{}", password);
        return result;
    }


    public static boolean isMatch(String regex, String text) {
        if (text == null) return false;
        return Pattern.matches(regex, text);
    }
}
