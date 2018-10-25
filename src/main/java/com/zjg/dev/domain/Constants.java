package com.zjg.dev.domain;

public abstract class Constants {
    /**
     * TOP默认时间格式
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * TOP Date默认时区
     */
    public static final String DATE_TIMEZONE = "GMT+8";

    /**
     * UTF-8字符集
     */
    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * GBK字符集
     */
    public static final String CHARSET_GBK = "GBK";

    /**
     * TOP JSON 应格式
     */
    public static final String FORMAT_JSON = "json";

    /**
     * TOP XML 应格式
     */
    public static final String FORMAT_XML = "xml";

    /**
     * MD5签名方式
     */
    public static final String SIGN_METHOD_MD5 = "md5";

    /**
     * HMAC签名方式
     */
    public static final String SIGN_METHOD_HMAC = "hmac";

    //签名密码
    public static final String SIGN_APP_SECRET = "abcd";
    public static final String SIGN_HMAC_SHA1 = "HmacSHA1";


    /****************************************************************
     * 操作类型
     * ****************************************************************
     */
    public static final byte ADD = 0;
    public static final byte DELETE = 1;
    public static final byte UPDATE = 2;
    public static final byte SELECT = 3;

    public static final String FILEUPLOAD = "/tmp/";

}

