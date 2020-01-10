package com.example.rbacdemo.web.controller;

import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.entity.User;
import com.example.rbacdemo.service.model.TokenUser;
import com.example.rbacdemo.service.UserRoleService;
import com.example.rbacdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private UserRoleService userRoleService;

    @PostMapping("/accountLogin")
    public Result<TokenUser> accountLogin(String account, String password, Integer appId) {
        return userService.accountLogin(account, password, appId);
    }

    @PostMapping("/tokenLogin")
    public Result<TokenUser> tokenLogin(String token) {
        return userService.tokenLogin(token);
    }

    @PostMapping("/list")
    public Result list(String account, String userName,
                       Date bCreateTime, Date eCreateTime,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        PageData<User> pageData = userService.find(account, userName, bCreateTime, eCreateTime, pageNum, pageSize);
        return Result.success(pageData);
    }

    @PostMapping("/add")
    public Result add(String account, String userName, String password) {
        return userService.add(account, userName, password);
    }

    @PostMapping("/modify")
    public Result modify(int userId, String account, String userName, String password) {
        return userService.modify(userId, account, userName, password);
    }

    @PostMapping("/delete")
    public Result delete(int userId) {
        return userService.delete(userId);
    }

    @PostMapping("/batchDelete")
    public Result batchDelete(Integer[] userIds) {
        return userService.batchDelete(userIds);
    }

    @PostMapping("/roleIdList")
    public Result roleIdList(int userId) {
        List<Integer> userRoleIdList = userRoleService.findRoleIdByUserId(userId);
        return Result.success(userRoleIdList);
    }

    @PostMapping("/roleSave")
    public Result roleSave(int userId, Integer[] roleIds) {
        return userRoleService.save(userId, roleIds);
    }


    @PostMapping("/logout")
    public Result logout(@RequestHeader String token) {
        return userService.logout(token);
    }

    /* Setters */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
}
