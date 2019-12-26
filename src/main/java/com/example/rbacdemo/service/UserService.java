package com.example.rbacdemo.service;

import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.entity.User;

import java.util.Date;

public interface UserService {
    PageData<User> find(String account, String userName, Date bCreateTime, Date eCreateTime, int pageNum, int pageSize);

    User get(int userId);

    boolean existAccount(String account);

    Result<User> add(String account, String userName, String password);

    Result<User> modify(int userId, String account, String userName, String password);

    Result delete(int userId);

    Result batchDelete(Integer[] userIds);
}
