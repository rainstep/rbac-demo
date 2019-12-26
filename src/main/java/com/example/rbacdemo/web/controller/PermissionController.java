package com.example.rbacdemo.web.controller;

import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.entity.Permission;
import com.example.rbacdemo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    private PermissionService permissionService;

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/list")
    public Result list(String permissionName, String permissionCode, Integer resourceId, int pageNum, int pageSize) {
        PageData<Permission> pageData = permissionService.find(permissionName, permissionCode, resourceId, pageNum, pageSize);
        return Result.success(pageData);
    }

    @PostMapping("/add")
    public Result add(String permissionName, String permissionCode, int resourceId) {
        return permissionService.add(permissionName, permissionCode, resourceId);
    }

    @PostMapping("/modify")
    public Result modify(int permissionId, String permissionName, String permissionCode, int resourceId) {
        return permissionService.modify(permissionId, permissionName, permissionCode, resourceId);
    }

    @PostMapping("/delete")
    public Result delete(int permissionId) {
        return permissionService.delete(permissionId);
    }
}
