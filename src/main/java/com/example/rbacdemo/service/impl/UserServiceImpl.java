package com.example.rbacdemo.service.impl;

import com.example.rbacdemo.entity.User;
import com.example.rbacdemo.service.UserService;
import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public PageData<User> list(String account, Integer pageNum, Integer pageSize) {
        return null;
    }
}
