package com.zjg.dev;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.zjg.dev.domain.RespData;
import com.zjg.dev.util.HttpUtils;
import com.zjg.dev.util.MD5Util;

import java.util.Map;
import java.util.TreeMap;

public class Test {

    String code = "CHJMJ";
    String appId = "ICECHJMJ-MVHB-KZTU-QMEC-ON4JVJ8IBG7A";
    String token = "R1zYKYFzpuLY4tbqgs3Y";
    String clientSecret = "wALSTcXiYPFYfk1TlsUE";
    String corpuuid = "a8c58297436f433787725a94f780a3c9";

    @org.junit.Test
    public void contextLoads1() {

        String url = "https://openapi.colourlife.com/v1/resourcems/unit/search?admin=1";
        Map params = Maps.newHashMap();
        params.put("token", "DAdXiLQpS6wq3IyGMhK4");
        params.put("communityUuid", "f96feb0d-2807-4ccd-bd06-8fa2b0496519");
        params.put("pageIndex", 0);
        params.put("pageSize", 999);
        if (url.indexOf("?") < 0) url += "?";
        if (null != params && !params.isEmpty()) {
            String param = Joiner.on("&").withKeyValueSeparator("=").join(params);
            url = url + param;
        }
        System.out.println(url);

    }

    @org.junit.Test
    public void contextLoads() {
        String url = "https://openapi.colourlife.com/v1/resourcems/unit/search";
        Map params = Maps.newHashMap();
        params.put("token", "DAdXiLQpS6wq3IyGMhK4");
        params.put("communityUuid", "f96feb0d-2807-4ccd-bd06-8fa2b0496519");
        params.put("pageIndex", 0);
        params.put("pageSize", 999);
        RespData s = HttpUtils.content(url, params, false);
        System.out.println(s);


    }

    @org.junit.Test
    public void auth() {
        Map<String, Object> params = new TreeMap<String, Object>();
        String ts = getTs();
        params.put("corp_uuid", corpuuid);
        params.put("app_uuid", appId);
        params.put("signature", MD5Util.md5Encode(appId + ts + token, "utf-8"));
        params.put("timestamp", ts);
        setCommonParams(params, ts, true);
        HttpUtils.post("https://openapi.colourlife.com/v1/authms/auth/app", params);

    }

    private void setCommonParams(Map<String, Object> params, String ts, boolean isIce) {
        params.put("sign", Md5Sign(appId, ts));
        params.put("ts", String.valueOf(ts));
        params.put("appID", appId);
        if (!isIce)
            params.put("signStr", Md5Sign2(appId, ts));
    }

    public String getTs() {
        String ts = String.valueOf(System.currentTimeMillis());
        ts = ts.substring(0, ts.length() - 3);
        return ts;
    }

    public String Md5Sign(String appID, String ts) {
        return MD5Util.md5Encode(appID + ts + token + "false", "utf-8");
    }


    public String Md5Sign2(String appID, String ts) {
        return MD5Util.md5Encode(appID + ts + "false", "utf-8");
    }
}
