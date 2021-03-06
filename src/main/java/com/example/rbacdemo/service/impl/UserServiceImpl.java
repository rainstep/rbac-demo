package com.example.rbacdemo.service.impl;

import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.common.util.AlgorithmUtils;
import com.example.rbacdemo.common.util.RandomUtils;
import com.example.rbacdemo.common.util.RegexUtils;
import com.example.rbacdemo.common.util.StringUtils;
import com.example.rbacdemo.dao.UserDao;
import com.example.rbacdemo.entity.User;
import com.example.rbacdemo.service.UserRoleService;
import com.example.rbacdemo.service.UserService;
import com.example.rbacdemo.service.UserTokenService;
import com.example.rbacdemo.service.model.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private UserRoleService userRoleService;
    private UserTokenService userTokenService;

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
    public Result<User> modify(int userId, String account, String userName, String password) {
        if (!RegexUtils.checkAccount(account)) return Result.error("账号以字母、下划线开头，只允许字母、数字和下划线，最少6位长度");
        if (StringUtils.isBlank(userName)) return Result.error("用户名不能为空");
        if (!RegexUtils.checkPassword(password)) return Result.error("密码只允许字母、数字和下划线，最少6位长度");
        User user = userDao.get(userId);
        if (user == null) return Result.error("该用户不存在");
        if (!user.getAccount().equals(account)) {
            if (userDao.existAccount(account)) return Result.error("该账号已被使用");
            user.setAccount(account);
        }
        user.setUserName(userName);
        if (!user.getPassword().equals(password)) {
            user.setPassword(this.getEncryptPassword(password, user.getPwdSalt()));
        }
        userDao.update(user);
        return Result.success(user);
    }

    @Transactional
    @Override
    public Result delete(int userId) {
        userDao.delete(userId);
        userRoleService.deleteByUserId(userId);
        return Result.success();
    }

    @Transactional
    @Override
    public Result batchDelete(Integer[] userIds) {
        if (userIds == null || userIds.length == 0) return Result.error("userIds不能为空");
        List<Integer> userIdList = Arrays.asList(userIds);
        userDao.batchDelete(userIdList);
        userRoleService.deleteByUserIdIn(userIdList);
        return Result.success();
    }

    @Override
    public Result<TokenUser> accountLogin(String account, String password, Integer appId) {
        if (StringUtils.isBlank(account)) return Result.error("账号不能为空");
        if (StringUtils.isBlank(password)) return Result.error("密码不能为空");
        if (appId == null) return Result.error("appId不能为空");
        User user = userDao.getByAccount(account);
        if (user == null) return Result.error("该账号不存在");
        String encryptPassword = this.getEncryptPassword(password, user.getPwdSalt());
        if (!user.getPassword().equals(encryptPassword)) return Result.error("密码有误");
        return userTokenService.login(user, appId);
    }

    @Override
    public Result<TokenUser> tokenLogin(String token) {
        if (StringUtils.isBlank(token)) return Result.error("token不能为空");
        return userTokenService.tokenLogin(token);
    }

    @Override
    public Result logout(String token) {
        return userTokenService.logout(token);
    }

    private String getEncryptPassword(String password, String pwdSalt) {
        return AlgorithmUtils.md5Encrypt(password + pwdSalt);
    }

    /* Setters */
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
    @Autowired
    public void setUserTokenService(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }


}
