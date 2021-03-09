package com.config;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.IOUtils;
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
        String requestId = request.getHeader(PortalConstants.TRACE_ID);
        if (null == requestId) {
            requestId = UuidGenerator.generateTraceId();
        }
        // 将requestId存入上下文中
        MDC.put(PortalConstants.TRACE_ID, requestId);

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

        ServletRequest requestWrapper = new CustomHttpServletRequestWrapper(request);
        BufferedReader bufferedReader = requestWrapper.getReader();
        String body = IOUtils.read(bufferedReader);
        log.info("request body is [{}]", body);

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        CustomHttpServletResponseWrapper responseWrapper= new CustomHttpServletResponseWrapper(response);
        BaseResponse baseResponse = null;
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
            log.info("data is [{}]", new String(responseWrapper.getBytes()));
            baseResponse = new BaseResponse(requestId, "200", "Success", JSONObject.parse(new String(responseWrapper.getBytes())));
        } catch (ServletException e) {
            if (e.getCause() instanceof CustomException) {
                CustomException customException =  (CustomException) e.getCause();
                baseResponse = new BaseResponse(requestId, customException.getErrorCode().getCode(), customException.getErrorCode().getMessage(), customException.getMessage());
            } else {
                log.error("other exception occur", e);
                baseResponse = new BaseResponse(requestId, ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getMessage(), "未知错误");
            }
        }

        log.info("baseResponse = [{}]", baseResponse);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JSONObject.toJSONString(baseResponse).getBytes());
        outputStream.flush();
        outputStream.close();
        log.info("after filter");
    }

    @Override
    public void destroy() {

    }
}
