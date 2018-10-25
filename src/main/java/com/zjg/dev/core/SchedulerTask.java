package com.zjg.dev.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SchedulerTask {
    /**
     * deviceCheckState:每三十分钟 检查一次设备状态. <br/>
     * 1 激活设备，心跳时差超过30分钟，表示该设备 连接超时 2 心跳无时差，并且该设备开门日志超过12小时时差，表示该设备 门没关
     *
     * @throws Exception
     * @author R.ZENG
     * @since JDK 1.8
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void devHeartbeat() {
        log.info("设备心跳");
    }

}
