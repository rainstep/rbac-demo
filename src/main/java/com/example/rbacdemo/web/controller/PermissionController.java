package com.example.rbacdemo.web.controller;

import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.entity.Permission;
import com.example.rbacdemo.entity.Resource;
import com.example.rbacdemo.service.PermissionService;
import com.example.rbacdemo.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    private PermissionService permissionService;
    private ResourceService resourceService;

    @PostMapping("/list")
    public Result list(String permissionName, String permissionCode, Integer resourceId, int pageNum, int pageSize) {
        PageData<Permission> pageData = permissionService.find(permissionName, permissionCode, resourceId, pageNum, pageSize);
        return Result.success(pageData);
    }

    @PostMapping("treeList")
    public Result treeList() {
        List<Resource> resourceList = resourceService.findAll();
        List<Permission> permissionList = permissionService.findAll();

        Map<Integer, List<Permission>> permissionMap = new HashMap<>();
        for (Permission permission : permissionList) {
            Integer resourceId = permission.getResourceId();
            List<Permission> list = permissionMap.computeIfAbsent(resourceId, k -> new ArrayList<>());
            list.add(permission);
        }
        List<Map> dataMapList = new ArrayList<>();
        for (Resource resource : resourceList) {
            Integer resourceId = resource.getResourceId();
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("resourceId", resourceId);
            dataMap.put("resourceName", resource.getResourceName());
            dataMap.put("permissionList", permissionMap.get(resourceId));
            dataMapList.add(dataMap);
        }
        return Result.success(dataMapList);

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


    /* Setters */
    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    @Autowired
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
}
