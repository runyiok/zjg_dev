package com.zjg.dev.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
/**
 * 注解
 *   kafka 用户，人脸数据接收记录日志
 *  推送设备记录，
 *  http异常请求记录
 *  定时任务日志记录等……
 */
public @interface Notes {
    String name() default "";

    byte value() default 0;
}
