package com.zjg.dev.service;

import java.util.Set;

public interface IRealmService {
    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    Set<String> getUserRoles(String userId);

    /**
     * 获取用户权限
     *
     * @param userId
     * @return
     */
    Set<String> getUserPermissions(String userId);

    /**
     * 是否是单账号登录
     *
     * @return
     */
    boolean isSingleUser();
}
