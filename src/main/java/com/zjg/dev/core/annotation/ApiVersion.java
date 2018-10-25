package com.zjg.dev.core.annotation;


import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
/**
 * 版本控制
 */
public @interface ApiVersion {

    int value();//版本号
}
