package com.zjg.dev.core;


import com.zjg.dev.core.version.CustRequestMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Bean
    public HandlerInterceptor getHandlerInterceptor() {
        return new CustHandInterceptor();
    }


    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //多个拦截器时 以此添加 执行顺序按添加顺序
        registry.addInterceptor(getHandlerInterceptor()).addPathPatterns("/**");
    }

    /**
     * 重写请求过处理的方法
     */
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping mapper = new CustRequestMapping();
        mapper.setOrder(0);
        mapper.setInterceptors(getHandlerInterceptor());
        return mapper;
    }

}
