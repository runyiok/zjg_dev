package com.zjg.dev.domain;

import lombok.Getter;

@Getter
public enum RespEnum {

    ERROR(100000, "未知错误"),
    ERROR_SIGN(100001, "签名错误"),
    ERROR_HTTP(100002, "HTTP ERROR"),
    ERROR_API(100003, "未知API"),

    NEED_LOGIN(100200, "未登录"),
    REPEAT_REGISTER(100201, "该用户已注册"),
    USER_NOT_EXIST(100202, "不存在该用户"),
    PASSWORD_ERROR(100203, "密码错误"),
    EMPTY_USERNAME(100204, "用户名为空"),
    EMPTY_PASSWORD(100205, "密码为空"),
    SUCCESS(0, "success"),

    SYSTEM_ERROR(-1, "系统错误"),

    PERMISSION_DENIED(100100, "token"),
    PERMISSION_TOKEN(100101, "token 已过期"),
    PERMISSION_AUTH(100102, "未认证");


    private int code;
    private String msg;

    RespEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
