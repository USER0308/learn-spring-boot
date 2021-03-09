package com.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String uuid;
    private String newName;
}
