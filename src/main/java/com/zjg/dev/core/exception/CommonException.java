package com.zjg.dev.core.exception;

import com.zjg.dev.domain.RespEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class CommonException extends RuntimeException {
    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String message;

    public CommonException(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public CommonException(String message) {
        this.message = message;
        this.code = RespEnum.SYSTEM_ERROR.getCode();
    }

    public CommonException() {
        super();
        this.code = RespEnum.SYSTEM_ERROR.getCode();
    }
}
