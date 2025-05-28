package com.example.handler.exception;


import com.example.domain.entity.ResponseResult;
import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
    log.error("出现了异常！{}",e);
    return ResponseResult.errorResult(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseResult ExceptionHandler(Exception e){
        log.error("出现了异常！{}",e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}
