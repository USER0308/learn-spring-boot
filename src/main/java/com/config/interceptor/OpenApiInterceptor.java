package com.config.interceptor;

import com.constants.ErrorCode;
import com.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class OpenApiInterceptor extends HandlerInterceptorAdapter {

    private String pubKey = "key";
    private String secKey = "secret";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("in open api interpreter, preHandle");
        // 从header中取出公钥、参数和签名，进行校验
        String key = request.getHeader("key");
        String sign = request.getHeader("sign");
        String timeStamp = request.getHeader("timeStamp");

        if (null == key || null == sign || null == timeStamp) {
            throw new CustomException(ErrorCode.PARAM_REQUIRED, "缺失key,sign或timestamp参数");
        }

        String content = "content";
        Map<String, String[]> paramMap = request.getParameterMap();
        while ( paramMap.entrySet().iterator().hasNext() ) {
            Map.Entry<String, String[]> entry = paramMap.entrySet().iterator().next();
            // set to content
            log.info("key=[{}], values=[{}]", entry.getKey(), entry.getValue());
        }


        // verify sign
        boolean verifyResult = verify(key, sign, timeStamp, content);
        if (!verifyResult) {
            throw new CustomException(ErrorCode.AUTHENTICATION_FAILURE, "请检查key,sign或timestamp参数");
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("in open api interpreter, postHandle");
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("in open api interpreter, afterCompletion");
        super.afterCompletion(request, response, handler, ex);
    }

    private boolean verify(String key, String sign, String timeStamp, String content) {
        // get secret by key
        // verify sign by secret
        return true;
    }
}
