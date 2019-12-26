package com.example.rbacdemo.service.impl;

import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.common.util.StringUtils;
import com.example.rbacdemo.dao.PermissionDao;
import com.example.rbacdemo.entity.Permission;
import com.example.rbacdemo.entity.Permission;
import com.example.rbacdemo.entity.Permission;
import com.example.rbacdemo.service.PermissionService;
import com.example.rbacdemo.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PermissionServiceImpl implements PermissionService {
    private PermissionDao permissionDao;
    @Autowired
    public void setPermissionDao(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    private RolePermissionService rolePermissionService;
    @Autowired
    public void setRolePermissionService(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @Override
    public PageData<Permission> find(String permissionName, String permissionCode, Integer resourceId, int pageNum, int pageSize) {
        return permissionDao.find(permissionName, permissionCode, resourceId, pageNum, pageSize);
    }

    @Override
    public Permission get(int permissionId) {
        return permissionDao.get(permissionId);
    }


    @Override
    public boolean existResourceId(int resourceId) {
        long count = permissionDao.countByResourceId(resourceId);
        return count > 0;
    }

    @Override
    public Result<Permission> add(String permissionName, String permissionCode, int resourceId) {
        if (StringUtils.isBlank(permissionName)) return Result.error("权限名不能为空");
        if (StringUtils.isBlank(permissionCode)) return Result.error("权限编码不能为空");
        if (permissionDao.existPermissionCode(permissionCode)) return Result.error("权限编码已被使用");
        Permission permission = new Permission();
        permission.setPermissionName(permissionName);
        permission.setPermissionCode(permissionCode);
        permission.setResourceId(resourceId);
        permissionDao.insert(permission);
        return Result.success(permission);
    }

    @Override
    public Result<Permission> modify(int permissionId, String permissionName, String permissionCode, int resourceId) {
        if (StringUtils.isBlank(permissionName)) return Result.error("权限名不能为空");
        if (StringUtils.isBlank(permissionCode)) return Result.error("权限编码不能为空");
        Permission permission = permissionDao.get(permissionId);
        if (permission == null) return Result.error("该权限不存在");
        if (!permission.getPermissionCode().equals(permissionCode)) {
            if (permissionDao.existPermissionCode(permissionCode)) return Result.error("权限编码已被使用");
            permission.setPermissionCode(permissionCode);
        }
        permission.setPermissionName(permissionName);
        permission.setResourceId(resourceId);
        permissionDao.update(permission);
        return Result.success(permission);
    }

    @Transactional
    @Override
    public Result<Permission> delete(int permissionId) {
        permissionDao.delete(permissionId);
        rolePermissionService.deleteByPermissionId(permissionId);
        return Result.success();
    }
}
