package com.example.rbacdemo.service;

import com.example.rbacdemo.common.Result;
import com.example.rbacdemo.entity.Resource;

import java.util.List;

public interface ResourceService {
    List<Resource> findAll();

    Result<Resource> add(String resourceName, int resourceType);

    Result<Resource> modify(int resourceId, String resourceName, int resourceType);

    Result delete(int resourceId);
}
