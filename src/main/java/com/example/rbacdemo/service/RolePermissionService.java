package com.example.rbacdemo.service;

import com.example.rbacdemo.common.Result;

import java.util.List;

public interface RolePermissionService {

    List<Integer> findPermissionIdByRoleId(int roleId);

    List<Integer> findPermissionIdByRoleIdIn(List<Integer> roleIdList);

    void deleteByRoleId(int roleId);

    void deleteByPermissionId(int permissionId);

    Result save(int roleId, Integer[] permissionIds);

    void batchInsert(int roleId, List<Integer> permissionIdList);
}
