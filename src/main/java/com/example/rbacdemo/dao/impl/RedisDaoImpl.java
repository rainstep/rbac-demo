package com.example.rbacdemo.dao.impl;

import com.example.rbacdemo.dao.RedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Repository
public class RedisDaoImpl implements RedisDao {
    private static final Logger logger = LoggerFactory.getLogger(RedisDaoImpl.class);

    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, int seconds) {
        stringRedisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public void setAt(String key, String value, long unixTime) {
        stringRedisTemplate.opsForValue().set(key, value);
        this.expireAt(key, unixTime);
    }

    @Override
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void expire(String key, int seconds) {
        stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    @Override
    public void expireAt(String key, long unixTime) {
        stringRedisTemplate.expireAt(key, new Date(unixTime));
    }

    @Override
    public void expireAt(String key, Date date) {
        stringRedisTemplate.expireAt(key, date);
    }

    @Override
    public long ttl(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    @Override
    public List<String> findKey(String keyPattern) {
        Set<String> keys = stringRedisTemplate.keys(keyPattern);
        return new ArrayList<>(keys);
    }

    @Override
    public boolean existKey(String key) {
        Set<String> keys = stringRedisTemplate.keys(key);
        return keys.isEmpty();
    }
}
