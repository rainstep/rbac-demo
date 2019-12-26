package com.example.rbacdemo.service;

public interface RolePermissionService {
    void deleteByRoleId(int roleId);

    void deleteByPermissionId(int permissionId);
}
