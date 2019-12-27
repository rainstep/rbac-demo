package com.example.rbacdemo.web.controller;

import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.entity.Role;
import com.example.rbacdemo.service.RolePermissionService;
import com.example.rbacdemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private RoleService roleService;
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    private RolePermissionService rolePermissionService;
    @Autowired
    public void setRolePermissionService(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @PostMapping("/list")
    public Result list() {
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

    @PostMapping("/saveRolePermission")
    public Result saveRolePermission(int roleId, Integer[] permissionIds) {
        return rolePermissionService.save(roleId, permissionIds);
    }
}

