package com.example.rbacdemo.dao;

import java.util.List;

public interface UserRoleDao {
    void deleteByUserId(int userId);

    void deleteByUserIdIn(List<Integer> userIdList);

    void deleteByRoleId(int roleId);
}
