package com.example.rbacdemo.dao.impl;

import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.common.util.PageUtils;
import com.example.rbacdemo.common.util.StringUtils;
import com.example.rbacdemo.dao.PermissionDao;
import com.example.rbacdemo.dao.mapper.PermissionMapper;
import com.example.rbacdemo.entity.Permission;
import com.example.rbacdemo.entity.PermissionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PermissionDaoImpl implements PermissionDao {
    private PermissionMapper permissionMapper;

    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public PageData<Permission> find(String permissionName, String permissionCode, Integer resourceId, int pageNum, int pageSize) {
        PermissionExample example = new PermissionExample();
        PermissionExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(permissionName)) {
            criteria.andPermissionNameEqualTo(permissionName);
        }
        if (StringUtils.isNotBlank(permissionCode)) {
            criteria.andPermissionCodeEqualTo(permissionCode);
        }
        if (resourceId != null) {
            criteria.andResourceIdEqualTo(resourceId);
        }
        long totalCount = permissionMapper.countByExample(example);
        example.setOffset(PageUtils.getOffset(pageNum, pageSize));
        example.setOffset(pageSize);
        List<Permission> list = permissionMapper.selectByExample(example);
        return new PageData<>(totalCount, list);
    }

    @Override
    public Permission get(int permissionId) {
        return permissionMapper.selectByPrimaryKey(permissionId);
    }

    @Override
    public long countByResourceId(int resourceId) {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andResourceIdEqualTo(resourceId);
        return permissionMapper.countByExample(example);
    }

    @Override
    public boolean existPermissionCode(String permissionCode) {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andPermissionCodeEqualTo(permissionCode);
        long count = permissionMapper.countByExample(example);
        return count > 0;
    }

    @Override
    public void insert(Permission permission) {
        permissionMapper.insert(permission);
    }

    @Override
    public void update(Permission permission) {
        permissionMapper.updateByPrimaryKey(permission);
    }

    @Override
    public void delete(int permissionId) {
        permissionMapper.deleteByPrimaryKey(permissionId);
    }
}
