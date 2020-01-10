package com.example.rbacdemo.dao;

import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.entity.User;

import java.util.Date;
import java.util.List;

public interface UserDao {
    PageData<User> find(String account, String userName, Date bCreateTime, Date eCreateTime, int pageNum, int pageSize);

    User get(int userId);

    User getByAccount(String account);

    boolean existAccount(String account);

    void insert(User user);

    void update(User user);

    void delete(int userId);

    void batchDelete(List<Integer> userIdList);
}
