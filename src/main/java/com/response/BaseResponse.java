package com.response;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private String requestId;
    private String code;
    private String message;
    private T data;

    public BaseResponse (String requestId, String code, String message, T data) {
        this.requestId = requestId;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(T data) {
        this(null, null, null, data);
    }
}
