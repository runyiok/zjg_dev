package com.zjg.dev.controller;

import com.zjg.dev.core.annotation.ApiVersion;
import com.zjg.dev.core.annotation.Logical;
import com.zjg.dev.core.annotation.Permissions;
import com.zjg.dev.core.annotation.SysLog;
import com.zjg.dev.core.token.SubjectUtils;
import com.zjg.dev.domain.Constants;
import com.zjg.dev.service.ISysLogService;
import com.zjg.dev.service.IUserService;
import com.zjg.dev.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * //是否有system权限
 * SubjectUtil.getInstance().hasPermission(userId, "system");
 * //是否有system或者front权限
 * SubjectUtil.getInstance().hasPermission(userId, new String[]{"system","front"}, Logical.OR);
 * //是否有admin或者user角色
 * SubjectUtil.getInstance().hasRole(userId, new String[]{"admin","user"}, Logical.OR)
 */

/**
 * 需要有system和front权限才能访问,logical可以不写,默认是AND
 */
//@Permissions(value={"system","front"}, logical= Logical.AND)

@RestController
@RequestMapping("/{version}")
@Slf4j
public class Login {

    @Autowired
    private IUserService userService;
    @Autowired
    private ISysLogService logService;

    @ApiVersion(1)
    @RequestMapping("/login")
    @SysLog(name = "登录", value = Constants.SELECT)
    public Object login(String account, String password) {
        String token = SubjectUtils.getInstance().createToken("123456789", DateUtils.getAppointDate(new Date(), 30 * 60 * 1000));  //第二个参数是到期时间
        logService.queryUser();
        return userService.queryUser() + "token=" + token;
    }


    @ApiVersion(1)
    @RequestMapping("/index")
    @Permissions(value = {"system", "front"}, logical = Logical.AND)
    public String index(String token) {

        log.info(SubjectUtils.getInstance().getTokenSubject(token));
        log.info(SubjectUtils.getInstance().getUserRoles("123456789") + "");
        log.info(SubjectUtils.getInstance().getUserPermissions("123456789") + "");

        return token;
    }
}
