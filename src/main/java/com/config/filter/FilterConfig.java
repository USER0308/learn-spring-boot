package com.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FilterConfig {
    /**
     * 过滤器，对所有请求设置requestId，并记录request参数和response参数
     * @return
     */
    @Bean
    public FilterRegistrationBean<GlobalFilter> registGlobalFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new GlobalFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<LoggerFilter> registLoggerFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new LoggerFilter());
        registrationBean.addUrlPatterns("/business/*");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
