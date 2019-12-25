package com.example.rbacdemo.service.impl;

import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.common.util.AlgorithmUtils;
import com.example.rbacdemo.common.util.RandomUtils;
import com.example.rbacdemo.common.util.RegexUtils;
import com.example.rbacdemo.common.util.StringUtils;
import com.example.rbacdemo.entity.User;
import com.example.rbacdemo.service.UserService;
import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public PageData<User> find(String account, String userName, Date bCreateTime, Date eCreateTime, int pageNum, int pageSize) {
        if (pageNum < 1) pageNum = 1;
        if (pageSize < 1) pageSize = 10;
        if (pageSize > 100) pageSize = 100;
        return userDao.find(account, userName, bCreateTime, eCreateTime, pageNum, pageSize);
    }

    @Override
    public User get(int userId) {
        return userDao.get(userId);
    }

    @Override
    public boolean existAccount(String account) {
        return userDao.existAccount(account);
    }

    @Override
    public Result<User> add(String account, String userName, String password) {
        if (!RegexUtils.checkAccount(account)) return Result.error("账号以字母、下划线开头，只允许字母、数字和下划线，最少6位长度");
        if (StringUtils.isBlank(userName)) return Result.error("用户名不能为空");
        if (!RegexUtils.checkPassword(password)) return Result.error("密码只允许字母、数字和下划线，最少6位长度");
        if (userDao.existAccount(account)) return Result.error("该账号已被使用");
        String pwdSalt = RandomUtils.getString(4);
        String encryptPassword = this.getEncryptPassword(password, pwdSalt);
        User user = new User();
        user.setAccount(account);
        user.setUserName(userName);
        user.setPassword(encryptPassword);
        user.setPwdSalt(pwdSalt);
        user.setCreateTime(new Date());
        userDao.insert(user);
        return Result.success(user);
    }

    @Override
    public Result<User> update(int userId, String account, String userName, String password) {
        if (!RegexUtils.checkAccount(account)) return Result.error("账号以字母、下划线开头，只允许字母、数字和下划线，最少6位长度");
        if (StringUtils.isBlank(userName)) return Result.error("用户名不能为空");
        if (!RegexUtils.checkPassword(password)) return Result.error("密码只允许字母、数字和下划线，最少6位长度");
        User user = userDao.get(userId);
        if (user == null) return Result.error("用户不存在");
        if (!user.getAccount().equals(account)) {
            if (this.existAccount(account)) return Result.error("该账号已被使用");
            user.setAccount(account);
        }
        user.setUserName(userName);
        if (!user.getPassword().equals(password)) {
            user.setPassword(this.getEncryptPassword(password, user.getPwdSalt()));
        }
        userDao.update(user);
        return Result.success(user);
    }

    @Override
    public Result delete(int userId) {
        userDao.delete(userId);
        return Result.success();
    }

    private String getEncryptPassword(String password, String pwdSalt) {
        return AlgorithmUtils.md5Encrypt(password + pwdSalt);
    }
}
