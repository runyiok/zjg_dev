package com.zjg.dev;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableScheduling//定时任务开启
@EnableAutoConfiguration
//@EnableTransactionManagement//开启事物(注解service层)
@EnableAspectJAutoProxy(proxyTargetClass = true)//开启异步切面 操作日志
@MapperScan("com.zjg.dev.dao")
@SpringBootApplication(exclude = {
        //禁用自动注入DataSource,开启多数据源
        DataSourceAutoConfiguration.class
})
public class ZjgDevApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZjgDevApplication.class, args);
    }
}
