package com.example.rbacdemo.service;

import com.example.rbacdemo.common.Result;

import java.util.List;

public interface UserRoleService {

    List<Integer> findRoleIdByUserId(int userId);

    void deleteByUserId(int userId);

    void deleteByUserIdIn(List<Integer> userIdList);

    void deleteByRoleId(int roleId);

    Result save(int userId, Integer[] roleIds);

    void batchInsert(int userId, List<Integer> roleIdList);
}
