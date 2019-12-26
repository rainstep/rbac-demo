package com.example.rbacdemo.web.controller;

import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.entity.Resource;
import com.example.rbacdemo.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    private ResourceService resourceService;

    @Autowired
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping("/list")
    public Result list() {
        List<Resource> list = resourceService.findAll();
        return Result.success(list);
    }

    @PostMapping("/add")
    public Result add(String resourceName, int resourceType) {
        return resourceService.add(resourceName, resourceType);
    }

    @PostMapping("/modify")
    public Result modify(int resourceId, String resourceName, int resourceType) {
        return resourceService.modify(resourceId, resourceName, resourceType);
    }

    @PostMapping("/delete")
    public Result delete(int resourceId) {
        return resourceService.delete(resourceId);
    }
}
