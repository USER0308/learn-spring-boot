package com.utils;

import okhttp3.*;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtil {
    public final static int READ_TIMEOUT = 10;
    public final static int CONNECT_TIMEOUT = 10;
    public final static int WRITE_TIMEOUT = 10;
    public final static int RETRY_TIME = 3;

    public final static MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public final static MediaType MEDIA_TYPE_XML = MediaType.parse("application/xml; charset=utf-8");

    public static Response get(String url) {
        return get(url, null, null);
    }

    public static Response get(String url, Map<String, String> params) {
        return get(url, params, null);
    }

    public static Response get(String url, Map<String, String> params, Map<String, String> headers) {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        Request.Builder requestBuilder = new Request.Builder().get();
        if (null != headers) {
            requestBuilder.headers(Headers.of(headers));
        }
        StringBuilder sb = new StringBuilder(url);
        if (!CollectionUtils.isEmpty(params)) {
            boolean firstParam = true;
            for(String key : params.keySet()) {
                if (firstParam) {
                    sb.append("?").append(key).append("=").append(params.get(key));
                    firstParam = false;
                } else {
                    sb.append("&").append(key).append("=").append(params.get(key));
                }
            }
        }
        try {
            Response response = client.newCall(requestBuilder.url(sb.toString()).build()).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Response post(String url) {
        return post(url, null, null);
    }

    public static Response post(String url, String json) {
        return post(url, json, MEDIA_TYPE_JSON);
    }

    public static Response post(String url, String data, MediaType mediaType) {
        RequestBody requestBody = RequestBody.create(mediaType, data);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        Request.Builder requestBuilder = new Request.Builder().post(requestBody).url(url);
        try {
            Response response = client.newCall(requestBuilder.build()).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
