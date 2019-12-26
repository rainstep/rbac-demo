package com.example.rbacdemo.dao.impl;

import com.example.rbacdemo.common.PageData;
import com.example.rbacdemo.common.util.ListUtils;
import com.example.rbacdemo.common.util.PageUtils;
import com.example.rbacdemo.common.util.StringUtils;
import com.example.rbacdemo.dao.UserDao;
import com.example.rbacdemo.dao.mapper.UserMapper;
import com.example.rbacdemo.entity.User;
import com.example.rbacdemo.entity.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public PageData<User> find(String account, String userName, Date bCreateTime, Date eCreateTime, int pageNum, int pageSize) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(account)) criteria.andAccountEqualTo(account);
        if (StringUtils.isNotBlank(userName)) criteria.andUserNameEqualTo(userName);
        if (bCreateTime != null) criteria.andCreateTimeGreaterThanOrEqualTo(bCreateTime);
        if (eCreateTime != null) criteria.andCreateTimeLessThanOrEqualTo(eCreateTime);
        long totalCount = userMapper.countByExample(example);
        example.setOffset(PageUtils.getOffset(pageNum, pageSize));
        example.setLimit(pageSize);
//        example.setOrderByClause("create_time desc");
        List<User> list = userMapper.selectByExample(example);
        return new PageData<>(totalCount, list);
    }

    @Override
    public User get(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public boolean existAccount(String account) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountEqualTo(account);
        long count = userMapper.countByExample(example);
        return count > 0;
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public void delete(int userId) {
        userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public void batchDelete(List<Integer> userIdList) {
        if (ListUtils.isEmpty(userIdList)) return;
        UserExample example = new UserExample();
        example.createCriteria().andUserIdIn(userIdList);
        userMapper.deleteByExample(example);
    }
}
