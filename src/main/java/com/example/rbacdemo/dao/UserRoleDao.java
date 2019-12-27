package com.example.rbacdemo.dao;

import com.example.rbacdemo.entity.UserRole;

import java.util.List;

public interface UserRoleDao {
    List<UserRole> findByUserId(int userId);

    List<Integer> findRoleIdByUserId(int userId);

    void deleteByUserId(int userId);

    void deleteByUserIdIn(List<Integer> userIdList);

    void deleteByRoleId(int roleId);

    void deleteByUserIdAndRoleIdIn(int userId, List<Integer> roleIdList);

    void batchInsert(List<UserRole> list);
}
