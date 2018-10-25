package com.zjg.dev.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Strings;
import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;

import java.util.List;
import java.util.Map;

/**
 * fastjson工具类
 *
 * @version:1.0.0
 */
public final class FastJsonUtils {

    private static final SerializeConfig config;
    private static final SerializerFeature[] features = {SerializerFeature.WriteMapNullValue, // 输出空置字段
            SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
    };

    static {
        config = new SerializeConfig();
        config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
    }

    public static String toJSONString(Object object) {
        if (null == object) return null;
        return JSON.toJSONString(object, config, features);
    }

    public static String toJSONNoFeatures(Object object) {
        if (null == object) return null;
        return JSON.toJSONString(object, config);
    }


    public static Object toBean(String text) {
        if (Strings.isNullOrEmpty(text)) return null;
        return JSON.parse(text);
    }

    public static <T> T toBean(String text, Class<T> clazz) {

        return JSON.parseObject(text, clazz);
    }

    // 转换为数组
    public static <T> Object[] toArray(String text) {
        return toArray(text, null);
    }

    // 转换为数组
    public static <T> Object[] toArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz).toArray();
    }

    // 转换为List
    public static <T> List<T> toList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    /**
     * 将javabean转化为序列化的json字符串
     *
     * @param keyvalue
     * @return
     */
    public static Object beanToJson(KeyValue keyvalue) {
        String textJson = JSON.toJSONString(keyvalue);
        Object objectJson = JSON.parse(textJson);
        return objectJson;
    }

    /**
     * 将string转化为序列化的json字符串
     *
     * @param
     * @return
     */
    public static Object textToJson(String text) {
        if (Strings.isNullOrEmpty(text)) return null;
        return JSON.parse(text);
    }

    /**
     * json字符串转化为map
     *
     * @param
     * @return
     */
    public static Map stringToCollect(String text) {
        if (Strings.isNullOrEmpty(text)) return null;
        return JSONObject.parseObject(text);
    }

    /**
     * 将map转化为string
     *
     * @param m
     * @return
     */
    public static String collectToString(Map m) {
        if (null == m || m.isEmpty()) return null;
        return JSONObject.toJSONString(m);
    }
}
