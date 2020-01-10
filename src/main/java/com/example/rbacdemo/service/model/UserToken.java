package com.example.rbacdemo.service.model;

import com.example.rbacdemo.common.exception.TokenInvalidException;
import com.example.rbacdemo.common.util.AlgorithmUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserToken {
    private static final Logger logger = LoggerFactory.getLogger(UserToken.class);

    private static final String PREFIX = "rbac_user_token";
    private static final String SEPARATOR = "-";

    private Integer userId;
    private Integer appId;
    private long createTimeMillis = System.currentTimeMillis();
    private String token;

    private UserToken() {}

    public static UserToken getInstance(String encryptedToken) {
        if (encryptedToken == null) return null;
        String token;
        try {
            token = AlgorithmUtils.aesDecrypt(encryptedToken);
        } catch (Exception ex) {
            logger.error("token解密失败");
            return null;
        }
        return getUserToken(token);
    }

    public static UserToken getUserToken(String token) {
        String[] args = token.split(SEPARATOR);
        if (args.length != 4) return null;
        String prefix = args[0];
        int userId = Integer.parseInt(args[1]);
        int appId = Integer.parseInt(args[2]);
        long createTimeMillis = Long.parseLong(args[3]);
        if (!PREFIX.equals(prefix)) {
            logger.error("token前缀错误");
            return null;
        }
        UserToken userToken = new UserToken();
        userToken.userId = userId;
        userToken.appId = appId;
        userToken.createTimeMillis = createTimeMillis;
        userToken.token = token;
        return userToken;
    }


    public static UserToken getInstance(Integer userId, Integer appId) {
        if (userId == null || appId == null) return null;
        UserToken userToken = new UserToken();
        userToken.userId = userId;
        userToken.appId = appId;
        userToken.token = PREFIX +
                SEPARATOR +
                userToken.userId +
                SEPARATOR +
                userToken.appId +
                SEPARATOR +
                userToken.createTimeMillis;
        return userToken;
    }

    public long getCreateTimeMillis() {
        return createTimeMillis;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getAppId() {
        return appId;
    }

    public String getToken() {
        return token;
    }

    public String getTokenKeyPatternByAppId(Integer appId) {
        String builder = PREFIX +
                SEPARATOR +
                userId +
                SEPARATOR +
                appId +
                SEPARATOR +
                "*";
        return builder;
    }

    public String getEncryptToken() {
        return AlgorithmUtils.aesEncrypt(this.token);
    }
}
