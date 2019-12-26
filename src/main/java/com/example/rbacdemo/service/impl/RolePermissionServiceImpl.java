package com.example.rbacdemo.service.impl;

import com.example.rbacdemo.dao.RolePermissionDao;
import com.example.rbacdemo.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolePermissionServiceImpl implements RolePermissionService {
    private RolePermissionDao rolePermissionDao;
    @Autowired
    public void setRolePermissionDao(RolePermissionDao rolePermissionDao) {
        this.rolePermissionDao = rolePermissionDao;
    }

    @Override
    public void deleteByRoleId(int roleId) {
        rolePermissionDao.deleteByRoleId(roleId);
    }

    @Override
    public void deleteByPermissionId(int permissionId) {
        rolePermissionDao.deleteByPermissionId(permissionId);
    }
}
