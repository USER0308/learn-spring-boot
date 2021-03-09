package com.exception;

import com.constants.ErrorCode;
import lombok.Data;

@Data
public class CustomException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public CustomException() {}

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public CustomException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
