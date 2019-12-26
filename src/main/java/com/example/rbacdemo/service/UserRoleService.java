package com.example.rbacdemo.service;

import java.util.List;

public interface UserRoleService {
    void deleteByUserId(int userId);

    void deleteByUserIdIn(List<Integer> userIdList);

    void deleteByRoleId(int roleId);
}
