package com.utils;

import com.exception.CustomException;
import com.constants.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * 生成的uuid
 *
 */
@Slf4j
public class UuidGenerator {

    public static String generateWithName(String name) {
        return name + "-" + generateUuid(8);
    }

    public static String generateLongUuid() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
//        log.info("uuid string is [{}]", uuidString);
        // cut off the "-"
        uuidString = uuidString.replaceAll("-", "");
//        log.info("after cutting off the -, uuid string now is [{}]", uuidString);
        return uuidString;
    }

    public  static String generateUuid(int num) {
        if (num > 20 || num < 1) {
            throw new CustomException(ErrorCode.INVALID_PARAM, "uuid位数不符");
        }
        return generateLongUuid().substring(0, num);
    }

    public static String genetateShortUuid() {
        return generateUuid(8);
    }

    public static String generateTraceId() {
        return generateUuid(16);
    }
}
