package com.zjg.dev.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ICacheService {

    // 获取缓存的集合
    List<String> getSet(String key);

    // 把集合加入缓存
    boolean putSet(String key, Set<String> values);

    // 删除集合的某一元素
    boolean removeSet(String key, String value);

    // 清除缓存
    boolean delete(String key);

    boolean delete(Collection<String> keys);

    // 获取匹配keys
    Set<String> keys(String pattern);
}
