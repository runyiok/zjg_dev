package com.zjg.dev.service.impl;


import com.google.common.collect.Sets;
import com.zjg.dev.core.token.SubjectUtils;
import com.zjg.dev.service.IRealmService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RealmServiceImpl implements IRealmService {
    //@Autowired
    // private IUserService userService;
    //@Autowired
    //private PermissionService permissionService;
    @Override
    public Set<String> getUserRoles(String userId) {
        Set<String> roles = Sets.newHashSet();
        //roles.add(userService.getUserById(userId).getRoleId());
        roles.add("123456");
        return roles;
    }

    @Override
    public Set<String> getUserPermissions(String userId) {
        Set<String> permissionValues = new HashSet<String>();
        List<String> userRoles = SubjectUtils.getInstance().getUserRoles(userId);
        if (userRoles.size() > 0) {
            permissionValues.add("999999");
            // List<Permission> permissions = permissionService.getPermissionsByRoleId(userRoles.get(0));
            // for (int i = 0; i < permissions.size(); i++) {
            //  permissionValues.add(permissions.get(i).getPermissionValue());
            //  }
        }
        return permissionValues;
    }

    @Override
    public boolean isSingleUser() {
        return false;
    }
}
