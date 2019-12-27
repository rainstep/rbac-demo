package com.example.rbacdemo.dao.impl;

import com.example.rbacdemo.common.util.ListUtils;
import com.example.rbacdemo.dao.RolePermissionDao;
import com.example.rbacdemo.dao.mapper.RolePermissionMapper;
import com.example.rbacdemo.entity.RolePermission;
import com.example.rbacdemo.entity.RolePermissionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RolePermissionDaoImpl implements RolePermissionDao {
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    public void setRolePermissionMapper(RolePermissionMapper rolePermissionMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Override
    public List<RolePermission> findByRoleId(int roleId) {
        RolePermissionExample example = new RolePermissionExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        return rolePermissionMapper.selectByExample(example);
    }

    @Override
    public List<Integer> findPermissionIdByRoleId(int roleId) {
        List<RolePermission> list = this.findByRoleId(roleId);
        List<Integer> permissionIdList = list.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        return permissionIdList;
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

    @Override
    public void deleteByUserIdAndRoleIdIn(int roleId, List<Integer> permissionIdList) {
        if (ListUtils.isEmpty(permissionIdList)) return;
        RolePermissionExample example = new RolePermissionExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andPermissionIdIn(permissionIdList);
        rolePermissionMapper.deleteByExample(example);
    }

    @Override
    public void batchInsert(List<RolePermission> list) {
        if (ListUtils.isEmpty(list)) return;
        rolePermissionMapper.batchInsert(list);
    }
}
