package com.example.rbacdemo.web.controller;

import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.entity.User;
import com.example.rbacdemo.service.UserService;
import com.example.rbacdemo.common.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result<User> login(String account, String password) {
        return Result.success();
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

    @PostMapping("batchDelete")
    public Result batchDelete(Integer[] userIds) {
        return userService.batchDelete(userIds);
    }

}
