package com.zjg.dev.service.impl;

import com.google.common.collect.Maps;
import com.zjg.dev.domain.SipConf;
import com.zjg.dev.service.ISipService;
import com.zjg.dev.util.FastJsonUtils;
import com.zjg.dev.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SipServiceImpl implements ISipService {
    @Autowired
    private SipConf sip;

    @Override
    public String createPhone(String e164, String password, String displayNumber, String memo) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("e164", e164);
        param.put("password", password);
        param.put("displayNumber", "");
        param.put("memo", "");
        return params(param);
    }

    @Override
    public String getPhoneOnline(String[] e164s) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("e164", e164s);
        return params(param);
    }

    @Override
    public String modifyPhone(String e164, short lockType) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("e164", e164);
        param.put("lockType", lockType);
        return params(param);
    }

    @Override
    public String deletePhone(String e164) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("e164", e164);
        return params(param);
    }

    private String params(Map<String, Object> param) {
        StringBuilder sb = new StringBuilder(sip.getUrl()).append("?tenant_id=").append(sip.getTenantId()).append("&token=")
                .append(sip.getToken());
        return HttpUtils.post(sb.toString(), FastJsonUtils.toJSONString(param));
    }

}
