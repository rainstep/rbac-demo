package com.example.rbacdemo.service.impl;

import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.common.util.ListUtils;
import com.example.rbacdemo.dao.UserRoleDao;
import com.example.rbacdemo.entity.UserRole;
import com.example.rbacdemo.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private UserRoleDao userRoleDao;


    @Override
    public List<Integer> findRoleIdByUserId(int userId) {
        return userRoleDao.findRoleIdByUserId(userId);
    }

    @Override
    public void deleteByUserId(int userId) {
        userRoleDao.deleteByUserId(userId);
    }

    @Override
    public void deleteByUserIdIn(List<Integer> userIdList) {
        userRoleDao.deleteByUserIdIn(userIdList);
    }

    @Override
    public void deleteByRoleId(int roleId) {
        userRoleDao.deleteByRoleId(roleId);
    }

    @Transactional
    @Override
    public Result save(int userId, Integer[] roleIds) {
        List<Integer> roleIdList = roleIds == null ? new ArrayList<>() : ListUtils.arrayToList(roleIds);
        List<Integer> oldRoleIdList = userRoleDao.findRoleIdByUserId(userId);
        List<Integer> addRoleIdList = ListUtils.subtract(roleIdList, oldRoleIdList);
        List<Integer> delRoleIdList = ListUtils.subtract(oldRoleIdList, roleIdList);
        if (ListUtils.isNotEmpty(addRoleIdList)) {
            this.batchInsert(userId, addRoleIdList);
        }
        if (ListUtils.isNotEmpty(delRoleIdList)) {
            userRoleDao.deleteByUserIdAndRoleIdIn(userId, delRoleIdList);
        }
        return Result.success();
    }

    @Override
    public void batchInsert(int userId, List<Integer> roleIdList) {
        if (ListUtils.isEmpty(roleIdList)) return;
        List<UserRole> list = new ArrayList<>(roleIdList.size());
        for (Integer roleId : roleIdList) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            list.add(userRole);
        }
        userRoleDao.batchInsert(list);
    }

    /* Setters */
    @Autowired
    public void setUserRoleDao(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }
}
