package com.example.rbacdemo.dao;

import com.example.rbacdemo.entity.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findAll();

    Role get(int roleId);

    boolean existRoleCode(String roleCode);

    void insert(Role role);

    void update(Role role);

    void delete(int roleId);
}
