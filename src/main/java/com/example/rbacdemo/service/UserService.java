package com.example.rbacdemo.service;

import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.entity.User;

public interface UserService {
    PageData<User> list(String account, Integer pageNum, Integer pageSize);
}
