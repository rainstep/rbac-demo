package com.example.rbacdemo.dao;

import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.entity.Permission;

public interface PermissionDao {
    PageData<Permission> find(String permissionName, String permissionCode, Integer resourceId, int pageNum, int pageSize);

    Permission get(int permissionId);

    long countByResourceId(int resourceId);

    boolean existPermissionCode(String permissionCode);

    void insert(Permission permission);

    void update(Permission permission);

    void delete(int permissionId);
}
