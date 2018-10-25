package com.zjg.dev.core.exception;

import com.zjg.dev.domain.RespData;
import com.zjg.dev.domain.RespEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

    /**
     * 拦截 CommonException 的异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Object exceptionHandler(Exception ex) {
        log.info("CustomException：{}({})", ex.getMessage());
        if (ex instanceof CommonException)
            return RespData.error(((CommonException) ex).getCode(), ex.getMessage());
        return RespData.error(RespEnum.SYSTEM_ERROR.getCode(), RespEnum.SYSTEM_ERROR.getMsg());
    }
}
