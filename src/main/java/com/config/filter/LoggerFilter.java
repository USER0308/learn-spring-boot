package com.config.filter;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.IOUtils;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.common.CustomHttpServletRequestWrapper;
import com.common.CustomHttpServletResponseWrapper;
import com.constants.ErrorCode;
import com.constants.PortalConstants;
import com.exception.CustomException;
import com.response.BaseResponse;
import com.utils.UuidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class LoggerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("before filter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        setRequestId(request);
        logRequest(request);

        ServletRequest requestWrapper = new CustomHttpServletRequestWrapper(request);
        BufferedReader bufferedReader = requestWrapper.getReader();
        String body = IOUtils.read(bufferedReader);
        log.info("request body is [{}]", body);

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        CustomHttpServletResponseWrapper responseWrapper= new CustomHttpServletResponseWrapper(response);
        BaseResponse baseResponse;
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
            Object data = new String(responseWrapper.getBytes());
            log.info("data is [{}]", data);
            try {
                data = JSONObject.parse((String) data);
            } catch (JSONException e) {
                log.warn("not support json transfer");
            }
            baseResponse = new BaseResponse<>(getRequestId(), String.valueOf(responseWrapper.getStatus()), "Success", data);
        } catch (ServletException e) {
            if (e.getCause() instanceof CustomException) {
                CustomException customException =  (CustomException) e.getCause();
                baseResponse = new BaseResponse<>(getRequestId(),
                        customException.getErrorCode().getCode(),
                        customException.getErrorCode().getMessage(),
                        customException.getMessage());
            } else {
                log.error("other exception occur", e);
                baseResponse = new BaseResponse<>(getRequestId(),
                        ErrorCode.UNKNOWN_ERROR.getCode(),
                        ErrorCode.UNKNOWN_ERROR.getMessage(),
                        "未知错误");
            }
        }
        log.info("baseResponse is [{}]", baseResponse);
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] bytes = JSONObject.toJSONString(baseResponse).getBytes();
        response.setContentLength(bytes.length);
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
        log.info("after filter");
    }

    @Override
    public void destroy() {

    }

    private void setRequestId(HttpServletRequest request) {
        String requestId = request.getHeader(PortalConstants.TRACE_ID);
        if (null == requestId) {
            requestId = UuidGenerator.generateTraceId();
        }
        // 将requestId存入上下文中
        MDC.put(PortalConstants.TRACE_ID, requestId);
    }

    private String getRequestId() {
        return MDC.get(PortalConstants.TRACE_ID);
    }

    private void logRequest(HttpServletRequest request) {
        log.info("request method=[{}], path=[{}]", request.getMethod(), request.getServletPath());
        Enumeration<String> headNames = request.getHeaderNames();
        while(headNames.hasMoreElements()){
            String headName = headNames.nextElement();
            log.info("request header[{}]=[{}]", headName, request.getHeader(headName));
        }


        Map<String, String[]> paramMap = request.getParameterMap();
        if (CollectionUtils.isNotEmpty(paramMap.entrySet())) {
            log.info("request params includes ");
        }
        Iterator<Map.Entry<String, String[]>> iterator = paramMap.entrySet().iterator();
        while ( iterator.hasNext() ) {
            Map.Entry<String, String[]> entry = iterator.next();
            log.info("key=[{}], values=[{}]", entry.getKey(), entry.getValue());
        }
    }
}
