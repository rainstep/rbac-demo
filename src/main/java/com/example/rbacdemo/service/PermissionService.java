package com.example.rbacdemo.service;

import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.entity.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAll();

    PageData<Permission> find(String permissionName, String permissionCode, Integer resourceId, int pageNum, int pageSize);

    List<Permission> findByUserId(int userId);

    List<Integer> findPermissionIdByUserId(int userId);

    Permission get(int permissionId);

    boolean existResourceId(int resourceId);

    Result<Permission> add(String permissionName, String permissionCode, int resourceId);

    Result<Permission> modify(int permissionId, String permissionName, String permissionCode, int resourceId);

    Result<Permission> delete(int permissionId);
}
