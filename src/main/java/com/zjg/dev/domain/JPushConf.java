package com.zjg.dev.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 极光配置
 */
@Component
public class JPushConf {
    public static String secret;
    public static String appkey;
    public static Long devsLiveTime = 60L;
    public static String sound = "sound.caf";

    @Value("${jpush.secret}")
    public void setSecret(String secret) {
        JPushConf.secret = secret;
    }

    @Value("${jpush.app.key}")
    public void setAppkey(String appkey) {
        JPushConf.appkey = appkey;
    }

    @Value("${jpush.app.live}")
    public void setDevsLiveTime(Long devsLiveTime) {
        JPushConf.devsLiveTime = devsLiveTime;
    }

    @Value("${jpush.ios.sound}")
    public void setSound(String sound) {
        JPushConf.sound = sound;
    }
}
