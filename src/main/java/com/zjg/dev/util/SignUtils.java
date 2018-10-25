package com.zjg.dev.util;

import com.google.common.base.Strings;
import com.zjg.dev.core.exception.CommonException;
import com.zjg.dev.domain.Constants;
import com.zjg.dev.domain.RespEnum;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;

public final class SignUtils {

    public static boolean verifySign(HttpServletRequest request) throws Exception {
        TreeMap<String, String> params = new TreeMap<String, String>();

        String signStr = request.getParameter("sign");
        if (Strings.isNullOrEmpty(signStr)) {
            throw new CommonException(RespEnum.ERROR_SIGN.getCode(), RespEnum.ERROR_SIGN.getMsg());
        }

        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paramName = enu.nextElement().trim();
            if (!paramName.equals("sign")) {
                params.put(paramName, URLDecoder.decode(request.getParameter(paramName), Constants.CHARSET_UTF8));
            }
        }
        if (!sign(Constants.SIGN_APP_SECRET, params).equals(signStr)) {
            return false;
        }
        return true;
    }

    public static String sign(String appSecret, TreeMap<String, String> params) throws Exception {
        StringBuilder paramValues = new StringBuilder();
        params.put("appSecret", appSecret);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramValues.append(entry.getValue());
        }
        // 4. hmac_sha1签名算法
        byte[] bytes = hamc_sha1(paramValues.toString(), appSecret);
        // 5. 将签名转为16进制字符串
        String str = hex(bytes);
        // 6. 将16进制签名转为大写字符
        String signature = str.toUpperCase();
        return signature;
    }

    /**
     * hmac_sha1签名算法
     *
     * @param data      需签名的字符串
     * @param encrypKey 签名密钥
     * @return 生成的签名
     * @throws Exception
     */
    private static byte[] hamc_sha1(String data, String encrypKey) throws Exception {
        byte[] keyBytes = encrypKey.getBytes(Constants.CHARSET_UTF8);
        SecretKey secretKey = new SecretKeySpec(keyBytes, Constants.SIGN_HMAC_SHA1);
        Mac mac = Mac.getInstance(Constants.SIGN_HMAC_SHA1);
        mac.init(secretKey);
        byte[] dataBytes = data.getBytes(Constants.CHARSET_UTF8);
        return mac.doFinal(dataBytes);
    }

    /**
     * byte数组转16进制
     *
     * @param bytes 需转换的数组
     * @return 16进制数
     */
    public static String hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 获取参数签名
     *
     * @return 参数签名的值
     * @throws Exception
     */
    public String getSignature(Map<String, Object> params) throws Exception {
        if (params == null || params.isEmpty()) {
            return null;
        }
        // 1. 拼接参数key、value
        List<String> paramList = new ArrayList<String>();
        for (Map.Entry<String, Object> each : params.entrySet()) {
            {
                if (each.getValue() != null) {
                    paramList.add(each.getKey() + each.getValue());
                } else {
                    paramList.add(each.getKey());
                }
            }
        }
        // 2. 对参数按首字顺序排序
        Collections.sort(paramList);
        // 3. 拼接，获取最终签名因子
        StringBuffer sb = new StringBuffer();
        for (String each : paramList) {
            sb.append(each);
        }
        // 4. hmac_sha1签名算法
        byte[] bytes = hamc_sha1(sb.toString(), Constants.SIGN_APP_SECRET);
        // 5. 将签名转为16进制字符串
        String str = hex(bytes);
        // 6. 将16进制签名转为大写字符
        String signature = str.toUpperCase();
        // 返回最终签名数据
        return signature;
    }
}
