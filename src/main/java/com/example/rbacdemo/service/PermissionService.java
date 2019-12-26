package com.example.rbacdemo.service;

import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.entity.Permission;

public interface PermissionService {
    PageData<Permission> find(String permissionName, String permissionCode, Integer resourceId, int pageNum, int pageSize);

    Permission get(int permissionId);

    boolean existResourceId(int resourceId);

    Result<Permission> add(String permissionName, String permissionCode, int resourceId);

    Result<Permission> modify(int permissionId, String permissionName, String permissionCode, int resourceId);

    Result<Permission> delete(int permissionId);
}
