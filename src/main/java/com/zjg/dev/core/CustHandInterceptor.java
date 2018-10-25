package com.zjg.dev.core;

import com.google.common.collect.Sets;
import com.zjg.dev.core.annotation.Logical;
import com.zjg.dev.core.annotation.Permissions;
import com.zjg.dev.core.annotation.Roles;
import com.zjg.dev.core.exception.CommonException;
import com.zjg.dev.core.token.SubjectUtils;
import com.zjg.dev.domain.RespEnum;
import com.zjg.dev.util.SignUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class CustHandInterceptor implements HandlerInterceptor {
    @Getter
    @Setter
    private boolean debug = false;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (this.debug) return true;
        if (!excludePathPatterns(request)) return true;
        //token 检查
        SubjectUtils subjectUtil = SubjectUtils.getInstance();
        String token = subjectUtil.getRequestToken(request);
        String userId = subjectUtil.parseToken(token).getSubject();
        // 检查权限
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            if (method != null) {
                if (!checkPermission(method, userId) || !checkRole(method, userId)) {
                    throw new CommonException(RespEnum.PERMISSION_AUTH.getCode(), RespEnum.PERMISSION_AUTH.getMsg());
                }
            }
        }
        //权限校验通过将userId存入request中方便controller获取
        request.setAttribute("userId", userId);
        return true;
    }

    //设置不进行登录拦截的路径：登录注册和验证码
    private boolean excludePathPatterns(HttpServletRequest request) {
        String basePath = request.getContextPath();
        String path = request.getRequestURI().substring(basePath.length());
        if (Sets.newHashSet("/", "/v1.0/login").contains(path)) return false;
        return true;
    }

    /**
     * 检查权限
     *
     * @param method
     * @param userId
     * @return
     */
    private boolean checkPermission(Method method, String userId) {
        Permissions annotation = method.getAnnotation(Permissions.class);
        if (annotation == null) {
            return true;
        }
        String[] requiresPermissions = annotation.value();
        Logical logical = annotation.logical();
        return SubjectUtils.getInstance().hasPermission(userId,
                requiresPermissions, logical);
    }

    /**
     * 检查角色
     *
     * @param method
     * @param userId
     * @return
     */
    private boolean checkRole(Method method, String userId) {
        Roles annotation = method.getAnnotation(Roles.class);
        if (annotation == null) {
            return true;
        }
        String[] requiresRoles = annotation.value();
        Logical logical = annotation.logical();
        return SubjectUtils.getInstance()
                .hasRole(userId, requiresRoles, logical);
    }

    private boolean checkSign(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //本地签名对比
        if (!SignUtils.verifySign(request)) {
            throw new CommonException(RespEnum.ERROR_SIGN.getCode(), RespEnum.ERROR_SIGN.getMsg());
        }
        return true;
    }
}
