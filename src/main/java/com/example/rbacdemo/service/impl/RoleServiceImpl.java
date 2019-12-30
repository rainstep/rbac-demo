package com.example.rbacdemo.service.impl;

import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.common.util.StringUtils;
import com.example.rbacdemo.dao.RoleDao;
import com.example.rbacdemo.entity.Role;
import com.example.rbacdemo.service.RolePermissionService;
import com.example.rbacdemo.service.RoleService;
import com.example.rbacdemo.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;
    private RolePermissionService rolePermissionService;
    private UserRoleService userRoleService;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Result<Role> add(String roleName, String roleCode) {
        if (StringUtils.isBlank(roleName)) return Result.error("角色名不能为空");
        if (StringUtils.isBlank(roleCode)) return Result.error("角色编码不能为空");
        if (roleDao.existRoleCode(roleCode)) return Result.error("角色编码已被使用");
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleCode(roleCode);
        roleDao.insert(role);
        return Result.success(role);
    }

    @Override
    public Result<Role> modify(int roleId, String roleName, String roleCode) {
        if (StringUtils.isBlank(roleName)) return Result.error("角色名不能为空");
        if (StringUtils.isBlank(roleCode)) return Result.error("角色编码不能为空");
        Role role = roleDao.get(roleId);
        if (role == null) return Result.error("该角色不存在");
        if (!role.getRoleCode().equals(roleCode)) {
            if (roleDao.existRoleCode(roleCode)) return Result.error("角色编码已被使用");
            role.setRoleCode(roleCode);
        }
        role.setRoleName(roleName);
        roleDao.update(role);
        return Result.success(role);
    }

    @Transactional
    @Override
    public Result delete(int roleId) {
        roleDao.delete(roleId);
        rolePermissionService.deleteByRoleId(roleId);
        userRoleService.deleteByRoleId(roleId);
        return Result.success();
    }

    /* Setters */
    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    @Autowired
    public void setRolePermissionService(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }
    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
}
