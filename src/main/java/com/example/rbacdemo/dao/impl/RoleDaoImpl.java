package com.example.rbacdemo.dao.impl;

import com.example.rbacdemo.dao.RoleDao;
import com.example.rbacdemo.dao.mapper.RoleMapper;
import com.example.rbacdemo.entity.Role;
import com.example.rbacdemo.entity.RoleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    private RoleMapper roleMapper;

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public List<Role> findAll() {
        return roleMapper.selectByExample(new RoleExample());
    }

    @Override
    public Role get(int roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public boolean existRoleCode(String roleCode) {
        RoleExample example = new RoleExample();
        example.createCriteria().andRoleCodeEqualTo(roleCode);
        long count = roleMapper.countByExample(example);
        return count > 0;
    }

    @Override
    public void insert(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void update(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void delete(int roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
    }
}
