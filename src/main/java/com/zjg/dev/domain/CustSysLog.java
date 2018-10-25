package com.zjg.dev.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 操作日志
 */
@Data
public class CustSysLog implements Serializable {

    private Long opId;
    private String userName; //用户名
    private String operation; //操作
    private String method; //方法名
    private String params; //参数
    private String ip; //ip地址

}
