package com.config;

import com.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * RestController增强，对controller抛出的异常进行增强处理，打印抛错信息
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public void handleCustomException(HttpServletResponse response, CustomException e) {
        log.warn("global exception handler [{}]", e.getMessage());
        throw e;
    }

}
