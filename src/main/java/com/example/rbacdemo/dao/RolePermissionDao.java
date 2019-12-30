package com.example.rbacdemo.dao;

import com.example.rbacdemo.entity.RolePermission;

import java.util.List;

public interface RolePermissionDao {
    List<RolePermission> findByRoleId(int roleId);

    List<Integer> findPermissionIdByRoleId(int roleId);

    List<RolePermission> findByRoleIdIn(List<Integer> roleIdList);

    List<Integer> findPermissionIdByRoleIdIn(List<Integer> roleIdList);

    void deleteByRoleId(int roleId);

    void deleteByPermissionId(int permissionId);

    void deleteByUserIdAndRoleIdIn(int roleId, List<Integer> permissionIdList);

    void batchInsert(List<RolePermission> list);
}
