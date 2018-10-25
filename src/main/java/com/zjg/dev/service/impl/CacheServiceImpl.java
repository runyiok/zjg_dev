package com.zjg.dev.service.impl;

import com.zjg.dev.service.ICacheService;
import com.zjg.dev.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class CacheServiceImpl implements ICacheService {
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<String> getSet(String key) {
        return redisUtils.lRange(key, 0, -1);
    }

    @Override
    public boolean putSet(String key, Set<String> values) {
        return redisUtils.lLeftPushAll(key, values) > 0;
    }

    @Override
    public boolean removeSet(String key, String value) {
        return redisUtils.lRemove(key, 0, value) > 0;
    }

    @Override
    public boolean delete(String key) {
        redisUtils.delete(key);
        return true;
    }

    @Override
    public boolean delete(Collection<String> keys) {
        redisUtils.delete(keys);
        return true;
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisUtils.keys(pattern);
    }
}
