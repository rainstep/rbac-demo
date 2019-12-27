package com.example.rbacdemo.dao.impl;

import com.example.rbacdemo.common.util.ListUtils;
import com.example.rbacdemo.dao.UserRoleDao;
import com.example.rbacdemo.dao.mapper.UserRoleMapper;
import com.example.rbacdemo.entity.UserRole;
import com.example.rbacdemo.entity.UserRoleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRoleDaoImpl implements UserRoleDao {
    private UserRoleMapper userRoleMapper;
    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public List<UserRole> findByUserId(int userId) {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return userRoleMapper.selectByExample(example);
    }

    @Override
    public List<Integer> findRoleIdByUserId(int userId) {
        List<UserRole> list = this.findByUserId(userId);
        List<Integer> roleIdList = list.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        return roleIdList;
    }

    @Override
    public void deleteByUserId(int userId) {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        userRoleMapper.deleteByExample(example);
    }

    @Override
    public void deleteByUserIdIn(List<Integer> userIdList) {
        if (ListUtils.isEmpty(userIdList)) return;
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdIn(userIdList);
        userRoleMapper.deleteByExample(example);
    }

    @Override
    public void deleteByRoleId(int roleId) {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        userRoleMapper.deleteByExample(example);
    }

    @Override
    public void deleteByUserIdAndRoleIdIn(int userId, List<Integer> roleIdList) {
        if (ListUtils.isEmpty(roleIdList)) return;
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId).andRoleIdIn(roleIdList);
        userRoleMapper.deleteByExample(example);
    }

    @Override
    public void batchInsert(List<UserRole> list) {
        if (ListUtils.isEmpty(list)) return;
        userRoleMapper.batchInsert(list);
    }
}
