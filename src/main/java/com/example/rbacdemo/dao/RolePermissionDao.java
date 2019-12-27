package com.example.rbacdemo.dao;

import com.example.rbacdemo.entity.RolePermission;

import java.util.List;

public interface RolePermissionDao {
    List<RolePermission> findByRoleId(int roleId);

    List<Integer> findPermissionIdByRoleId(int roleId);

    void deleteByRoleId(int roleId);

    void deleteByPermissionId(int permissionId);



    void deleteByUserIdAndRoleIdIn(int roleId, List<Integer> permissionIdList);

    void batchInsert(List<RolePermission> list);
}
