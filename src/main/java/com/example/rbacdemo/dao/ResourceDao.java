package com.example.rbacdemo.dao;

import com.example.rbacdemo.entity.Resource;

import java.util.List;

public interface ResourceDao {
    List<Resource> findAll();

    Resource get(int resourceId);

    void insert(Resource resource);

    void update(Resource resource);

    void delete(int resourceId);
}
