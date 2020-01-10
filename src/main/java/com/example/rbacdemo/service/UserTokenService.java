package com.example.rbacdemo.service;


import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.entity.User;
import com.example.rbacdemo.service.model.TokenUser;

public interface UserTokenService {

    Result<TokenUser> login(User user, Integer appId);

    Result<TokenUser> tokenLogin(String token);

    TokenUser getUser(String token);

    Result check(String token);

    void refresh(String token);

    void remove(String token);

    Result logout(String token);
}
