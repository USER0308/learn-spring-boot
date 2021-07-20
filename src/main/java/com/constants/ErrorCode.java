package com.constants;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {

    UNKNOWN_ERROR("A00001", "unknown error"),
    INVALID_PARAM("A00002", "invalid parameter"),
    NOT_FOUND("A00003", "not found"),
    PARAM_REQUIRED("A00004", "parameter required"),
    AUTHENTICATION_FAILURE("A00005", "authentication failure"),
    PERMISSION_DENY("A00006", "permission deny"),
    ALREADY_EXIST("A00007", "already exist")

    ;

    private String code;
    private String message;

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
