package com.zjg.dev.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class SipConf {
    @Value("sip.hj.url")
    public String url;
    @Value("sip.hj.prefix")
    public String prefix;
    @Value("sip.hj.tenantid")
    public String tenantId;
    @Value("sip.hj.token")
    public String token;
    @Value("sip.hj.pass")
    public String password;
}
