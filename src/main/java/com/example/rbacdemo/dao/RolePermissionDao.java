package com.example.rbacdemo.dao;

public interface RolePermissionDao {
    void deleteByRoleId(int roleId);

    void deleteByPermissionId(int permissionId);
}
