package com.example.rbacdemo.service.impl;

import com.example.rbacdemo.dao.UserRoleDao;
import com.example.rbacdemo.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private UserRoleDao userRoleDao;
    @Autowired
    public void setUserRoleDao(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
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
}
