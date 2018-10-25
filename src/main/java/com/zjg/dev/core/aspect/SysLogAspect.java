package com.zjg.dev.core.aspect;

import com.alibaba.fastjson.JSON;
import com.zjg.dev.core.annotation.SysLog;
import com.zjg.dev.dao.log.SysLogMapper;
import com.zjg.dev.domain.CustSysLog;
import com.zjg.dev.util.IPUitls;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 系统日志：切面处理类
 */
@Component
@Aspect
@Slf4j
public class SysLogAspect {

    @Autowired
    private SysLogMapper mapper;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation(com.zjg.dev.core.annotation.SysLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        //保存日志
        CustSysLog sysLog = new CustSysLog();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取操作
        SysLog myLog = method.getAnnotation(SysLog.class);
        if (myLog != null) {
            String value = myLog.name();
            sysLog.setOperation(value);//保存获取的操作
        }
        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        sysLog.setMethod(className + "." + methodName);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        sysLog.setParams(params);
        //获取用户名
        // sysLog.setUserName(ShiroUtils.getUserEntity().getUsername());
        //获取用户ip地址
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        request.getParameter("token");
        sysLog.setIp(IPUitls.getRemoteAddr(request));
        //调用service保存SysLog实体类到数据库
        //  mapper.insert(sysLog);
    }

    @Around("logPoinCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        Object obj = pjp.proceed();
        long end = System.currentTimeMillis();
        log.info("调用controller 方法：{}，\n参数：{}，\n执行耗时：{}毫秒", pjp.getSignature().toString(), Arrays.toString(pjp.getArgs()), end - begin);
        return obj;
    }
}


