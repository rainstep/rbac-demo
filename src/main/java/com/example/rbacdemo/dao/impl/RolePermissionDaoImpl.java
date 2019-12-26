package com.example.rbacdemo.dao.impl;

import com.example.rbacdemo.dao.RolePermissionDao;
import com.example.rbacdemo.dao.mapper.RolePermissionMapper;
import com.example.rbacdemo.entity.RolePermissionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RolePermissionDaoImpl implements RolePermissionDao {
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    public void setRolePermissionMapper(RolePermissionMapper rolePermissionMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Override
    public void deleteByRoleId(int roleId) {
        RolePermissionExample example = new RolePermissionExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        rolePermissionMapper.deleteByExample(example);
    }

    @Transactional
    @Override
    public void deleteByPermissionId(int permissionId) {
        RolePermissionExample example = new RolePermissionExample();
        example.createCriteria().andPermissionIdEqualTo(permissionId);
        rolePermissionMapper.deleteByExample(example);
    }
}
