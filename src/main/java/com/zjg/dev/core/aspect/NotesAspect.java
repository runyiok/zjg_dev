package com.zjg.dev.core.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 注解
 * kafka 用户，人脸数据接收记录日志
 * 推送设备记录，
 * http异常请求记录
 * 定时任务日志记录等……
 */
@Aspect
@Component
@Order(-10)
@Slf4j
public class NotesAspect {

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut(" @annotation(com.zjg.dev.core.annotation.Notes) ")
    public void logPoinCut() {
    }

    /**
     * 后置返回通知
     * 这里需要注意的是:
     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     */
    @AfterReturning(value = " logPoinCut() ", returning = "result")
    public void doAfterReturningAdvice1(JoinPoint joinPoint, Object result) {
        System.out.println("第一个后置返回通知的返回值：" + result);
    }
}

