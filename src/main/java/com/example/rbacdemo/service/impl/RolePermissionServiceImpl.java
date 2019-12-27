package com.example.rbacdemo.service.impl;

import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.common.util.ListUtils;
import com.example.rbacdemo.dao.RolePermissionDao;
import com.example.rbacdemo.entity.RolePermission;
import com.example.rbacdemo.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Result save(int roleId, Integer[] permissionIds) {
        List<Integer> permissionIdList = permissionIds == null
                ? new ArrayList<>()
                : ListUtils.arrayToList(permissionIds);
        List<Integer> oldPermissionIdList = rolePermissionDao.findPermissionIdByRoleId(roleId);
        List<Integer> addPermissionIdList = ListUtils.subtract(permissionIdList, oldPermissionIdList);
        List<Integer> delPermissionIdList = ListUtils.subtract(oldPermissionIdList, permissionIdList);
        if (ListUtils.isNotEmpty(addPermissionIdList)) {
            this.batchInsert(roleId, addPermissionIdList);
        }
        if (ListUtils.isNotEmpty(delPermissionIdList)) {
            rolePermissionDao.deleteByUserIdAndRoleIdIn(roleId, delPermissionIdList);
        }
        return Result.success();
    }

    @Override
    public void batchInsert(int roleId, List<Integer> permissionIdList) {
        if (ListUtils.isEmpty(permissionIdList)) return;
        List<RolePermission> list = new ArrayList<>(permissionIdList.size());
        for (Integer permissionId : permissionIdList) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setRoleId(permissionId);
            list.add(rolePermission);
        }
        rolePermissionDao.batchInsert(list);
    }
}
