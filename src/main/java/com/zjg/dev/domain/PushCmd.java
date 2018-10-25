package com.zjg.dev.domain;

/**
 * 极光推送给设备的命令
 */
public enum PushCmd {

    DEVS_PUSH_OPEN(10001),//开门命令
    DEVS_PUSH_RESART(10002),//设备重启
    DEVS_PUSH_PASS_RESET(10003);//密码重置

    private int code;

    private PushCmd(int code) {
        this.code = code;
    }
}
