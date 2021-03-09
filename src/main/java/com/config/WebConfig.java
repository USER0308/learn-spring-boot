package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new OpenApiInterpreter())
                .addPathPatterns("/openApi/**")
                .order(1);

        registry.addInterceptor(new GlobalInterpreter())
                .excludePathPatterns("/openApi/**")
                .addPathPatterns("/**");

    }
}