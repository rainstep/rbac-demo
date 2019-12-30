package com.example.rbacdemo.web.controller;

import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.entity.Role;
import com.example.rbacdemo.entity.RolePermission;
import com.example.rbacdemo.service.RolePermissionService;
import com.example.rbacdemo.service.RoleService;
import com.example.rbacdemo.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private RoleService roleService;
    private RolePermissionService rolePermissionService;

    @PostMapping("/listAll")
    public Result listAll() {
        List<Role> list = roleService.findAll();
        return Result.success(list);
    }

    @PostMapping("/add")
    public Result add(String roleName, String roleCode) {
        return roleService.add(roleName, roleCode);
    }

    @PostMapping("/modify")
    public Result modify(int roleId, String roleName, String roleCode) {
        return roleService.modify(roleId, roleName, roleCode);
    }

    @PostMapping("/delete")
    public Result delete(int roleId) {
        return roleService.delete(roleId);
    }

    @PostMapping("/permissionIdList")
    public Result permissionIdList(int roleId) {
        List<Integer> permissionIdList = rolePermissionService.findPermissionIdByRoleId(roleId);
        return Result.success(permissionIdList);
    }

    @PostMapping("/permissionSave")
    public Result permissionSave(int roleId, Integer[] permissionIds) {
        return rolePermissionService.save(roleId, permissionIds);
    }


    /* Setters */
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    @Autowired
    public void setRolePermissionService(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }
}

