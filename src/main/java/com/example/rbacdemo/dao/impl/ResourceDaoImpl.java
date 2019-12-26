package com.example.rbacdemo.dao.impl;

import com.example.rbacdemo.dao.ResourceDao;
import com.example.rbacdemo.dao.mapper.ResourceMapper;
import com.example.rbacdemo.entity.Resource;
import com.example.rbacdemo.entity.ResourceExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResourceDaoImpl implements ResourceDao {
    private ResourceMapper resourceMapper;

    @Autowired
    public void setResourceMapper(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    @Override
    public List<Resource> findAll() {
        return resourceMapper.selectByExample(new ResourceExample());
    }

    @Override
    public Resource get(int resourceId) {
        return resourceMapper.selectByPrimaryKey(resourceId);
    }

    @Override
    public void insert(Resource resource) {
        resourceMapper.insert(resource);
    }

    @Override
    public void update(Resource resource) {
        resourceMapper.updateByPrimaryKey(resource);
    }

    @Override
    public void delete(int resourceId) {

        resourceMapper.deleteByPrimaryKey(resourceId);
    }
}
