package com.example.rbacdemo.dao;

import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.entity.Permission;

import java.util.List;

public interface PermissionDao {
    List<Permission> findAll();

    PageData<Permission> find(String permissionName, String permissionCode, Integer resourceId, int pageNum, int pageSize);

    List<Permission> findByPermissionIdIn(List<Integer> permissionIdList);

    Permission get(int permissionId);

    long countByResourceId(int resourceId);

    boolean existPermissionCode(String permissionCode);

    void insert(Permission permission);

    void update(Permission permission);

    void delete(int permissionId);
}
