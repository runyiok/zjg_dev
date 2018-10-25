package com.zjg.dev.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class RespData implements Serializable {
    private int code = RespEnum.SYSTEM_ERROR.getCode();            // 结果编号
    private String message = "";    // 结果信息
    private Object content;


    public static RespData success() {
        RespData data = new RespData();
        data.setCode(RespEnum.SUCCESS.getCode());
        return success(null);
    }

    public static RespData success(Object data) {
        RespData respData = new RespData();
        respData.setCode(RespEnum.SUCCESS.getCode());
        respData.setContent(data);
        return respData;
    }

    public static RespData error(RespEnum respEnum) {
        return error(respEnum.getCode(), respEnum.getMsg());
    }

    public static RespData error(int code, String message) {
        RespData data = new RespData();
        data.setCode(code);
        data.setMessage(message);
        return data;
    }

}
