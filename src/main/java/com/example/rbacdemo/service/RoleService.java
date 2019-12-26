package com.example.rbacdemo.service;

import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Result<Role> add(String roleName, String roleCode);

    Result<Role> modify(int roleId, String roleName, String roleCode);

    Result delete(int roleId);
}
