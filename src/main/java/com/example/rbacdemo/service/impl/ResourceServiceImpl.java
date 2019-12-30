package com.example.rbacdemo.service.impl;

import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.common.util.StringUtils;
import com.example.rbacdemo.dao.ResourceDao;
import com.example.rbacdemo.entity.Resource;
import com.example.rbacdemo.service.PermissionService;
import com.example.rbacdemo.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResourceServiceImpl implements ResourceService {
    private ResourceDao resourceDao;
    private PermissionService permissionService;

    @Override
    public List<Resource> findAll() {
        return resourceDao.findAll();
    }

    @Override
    public Result<Resource> add(String resourceName, int resourceType) {
        if (StringUtils.isBlank(resourceName)) return Result.error("资源名不能为空");
        Resource resource = new Resource();
        resource.setResourceName(resourceName);
        resource.setResourceType(resourceType);
        resourceDao.insert(resource);
        return Result.success();
    }

    @Override
    public Result<Resource> modify(int resourceId, String resourceName, int resourceType) {
        if (StringUtils.isBlank(resourceName)) return Result.error("资源名不能为空");
        Resource resource = resourceDao.get(resourceId);
        if (resource == null) return Result.error("该资源不存在");
        resource.setResourceName(resourceName);
        resource.setResourceType(resourceType);
        resourceDao.update(resource);
        return Result.success(resource);
    }

    @Override
    public Result delete(int resourceId) {
        boolean existResourceId = permissionService.existResourceId(resourceId);
        if (existResourceId) return Result.error("该资源下存在对应权限，无法删除");
        resourceDao.delete(resourceId);
        return Result.success();
    }

    /* Setters */
    @Autowired
    public void setResourceDao(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
    }
    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
}
