package com.example.rbacdemo.dao;

import java.util.Date;
import java.util.List;

public interface RedisDao {
    void set(String key, String value);

    void set(String key, String value, int seconds);

    void setAt(String key, String value, long unixTime);

    String getString(String key);

    void delete(String key);

    void expire(String key, int seconds);

    void expireAt(String key, long unixTime);

    void expireAt(String key, Date date);

    long ttl(String key);

    List<String> findKey(String keyPattern);

    boolean existKey(String key);
}
