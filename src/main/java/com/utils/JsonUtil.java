package com.utils;

import com.alibaba.fastjson.JSONObject;
import com.constants.ErrorCode;
import com.exception.CustomException;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

public class JsonUtil {

    public static String toString(Object obj) {
        if (!(obj instanceof Serializable)) {
            throw new CustomException(ErrorCode.INVALID_PARAM);
        }

        return JSONObject.toJSONString(obj);
    }

    public static <T> T parse(String jsonString, Class<T> clazz) {
        return JSONObject.parseObject(jsonString, clazz);
    }

    /**
     * 把source合并到target中，如果source和target中包含相同的key，则value值为source中的value
     * @param source
     * @param target
     * @return
     */
    public static JSONObject merge(JSONObject source, JSONObject target) {
        if (null == source) {
            return target;
        }
        if (null == target) {
            return source;
        }
        Iterator<String> itKeys1 = target.keySet().iterator();
        String key, value;
        while(itKeys1.hasNext()){
            key = itKeys1.next();
            value = (String) source.get(key);

            target.put(key, value);
        }
        return target;
    }
//
//    public static JSONObject deepMerge(JSONObject source, JSONObject target) {
//        if (null == source) {
//            return target;
//        }
//        if (null == target) {
//            return source;
//        }
//        Iterator<String> itKeys1 = target.keySet().iterator();
//        String key, value;
//        while(itKeys1.hasNext()){
//            key = itKeys1.next();
//            // 判断是否为String
//            if (source.get(key) instanceof String) {
//                value = (String) source.get(key);
//            } else {
//                source.get(key);
//            }
//
//            target.put(key, value);
//        }
//        return target;
//    }
}
