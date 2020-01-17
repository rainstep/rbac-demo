package com.example.rbacdemo.service.impl;

import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.common.constant.Constants;
import com.example.rbacdemo.common.exception.TokenException;
import com.example.rbacdemo.common.util.JacksonUtils;
import com.example.rbacdemo.common.util.ListUtils;
import com.example.rbacdemo.common.util.StringUtils;
import com.example.rbacdemo.dao.RedisDao;
import com.example.rbacdemo.entity.User;
import com.example.rbacdemo.service.UserService;
import com.example.rbacdemo.service.UserTokenService;
import com.example.rbacdemo.service.model.TokenUser;
import com.example.rbacdemo.service.model.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTokenServiceImpl implements UserTokenService {
    private RedisDao redisDao;

    private UserService userService;

//    @Value("${redis.userToken.seconds}")
    private int seconds = 30 * 60;

    @Override
    public Result<TokenUser> login(User user, Integer appId) {
        if (user == null) return Result.error("user不能为空");
        if (appId == null) return Result.error("appId不能为空");
        TokenUser tokenUser = this.getTokenUser(user);
        UserToken userToken = UserToken.getInstance(user.getUserId(), appId);
        this.appIdFilter(userToken);
        tokenUser.setToken(userToken.getEncryptToken());
        redisDao.set(userToken.getToken(), JacksonUtils.stringify(tokenUser), seconds);
        return Result.success(tokenUser);
    }

    @Override
    public Result<TokenUser> tokenLogin(String encryptToken) {
        UserToken userToken = UserToken.getInstance(encryptToken);
        if (userToken == null) return Result.error("token无效");
        TokenUser tokenUser = this.getTokenUser(userToken);
        if (tokenUser == null) {
            boolean isSuccess = this.appIdFilter(userToken);
            if (!isSuccess) return Result.tokenExpired();
            User user = userService.get(userToken.getUserId());
            if (user == null) return Result.error("用户不存在");
            tokenUser = this.getTokenUser(user);
            tokenUser.setToken(encryptToken);
            redisDao.set(userToken.getToken(), JacksonUtils.stringify(tokenUser), seconds);
        }
        return Result.success(tokenUser);
    }

    @Override
    public TokenUser getUser(String encryptedToken) {
        UserToken userToken = UserToken.getInstance(encryptedToken);
        String tokenValue = redisDao.getString(userToken.getToken());
        return JacksonUtils.parse(tokenValue, TokenUser.class);
    }

    @Override
    public Result<TokenUser> check(String encryptedToken) {
        try {
            if (StringUtils.isBlank(encryptedToken)) return Result.tokenExpired();
            UserToken userToken = UserToken.getInstance(encryptedToken);
            if (userToken == null) return Result.error("token无效");
            String tokenValue = redisDao.getString(userToken.getToken());
            if (tokenValue == null)  return Result.tokenExpired();
            return Result.success(JacksonUtils.parse(tokenValue, TokenUser.class));
        } catch (TokenException e) {
            return Result.error(e.getMessage());
        }
    }

    @Override
    public void refresh(String encryptedToken) {
        UserToken userToken = UserToken.getInstance(encryptedToken);
        if (userToken == null) return;
        redisDao.expire(userToken.getToken(), seconds);
    }

    @Override
    public void remove(String encryptedToken) {
        UserToken userToken = UserToken.getInstance(encryptedToken);
        if (userToken == null) return;
        redisDao.delete(userToken.getToken());
    }

    @Override
    public Result logout(String encryptedToken) {
        UserToken userToken = UserToken.getInstance(encryptedToken);
        redisDao.delete(userToken.getToken());
        return Result.success();
    }

    private boolean appIdFilter(UserToken userToken) {
        Integer appId = userToken.getAppId();
        List<Integer> filterAppIdList = getFilterAppIdList(appId);
        if (!filterAppIdList.contains(userToken.getAppId())) return true;
        String tokenPattern = userToken.getTokenKeyPatternByAppId(appId);
        List<String> tokenList = redisDao.findKey(tokenPattern);
        if (ListUtils.isEmpty(tokenList)) return true;
        for (String tokenItem : tokenList) {
            if (tokenItem.equals(userToken.getToken())) continue;
            UserToken userTokenItem = UserToken.getUserToken(tokenItem);
            if (userTokenItem == null) continue;
            if(userToken.getCreateTimeMillis() < userTokenItem.getCreateTimeMillis()) return false;
            redisDao.delete(tokenItem);
        }
        return true;
    }

    private List<Integer> getFilterAppIdList(int appId) {
        List<Integer> appIdList = new ArrayList<>();
        if (appId == Constants.AppId.ANDROID
                || appId == Constants.AppId.IOS) {
            appIdList.add(Constants.AppId.ANDROID);
            appIdList.add(Constants.AppId.IOS);
        }
        return appIdList;
    }

    private TokenUser getTokenUser(User user) {
        if (user == null) return null;
        TokenUser tokenUser = new TokenUser();
        tokenUser.setUserId(user.getUserId());
        tokenUser.setUserName(user.getUserName());
        tokenUser.setAccount(user.getAccount());
        return tokenUser;
    }

    private TokenUser getTokenUser(UserToken userToken) {
        if (userToken == null) return null;
        String value = redisDao.getString(userToken.getToken());
        return JacksonUtils.parse(value, TokenUser.class);
    }


    @Autowired
    public void setRedisDao(RedisDao redisDao) {
        this.redisDao = redisDao;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
