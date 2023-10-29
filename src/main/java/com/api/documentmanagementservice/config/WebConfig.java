package com.api.documentmanagementservice.config;

import com.api.documentmanagementservice.interceptor.CustomLocaleInterceptor;
import com.api.documentmanagementservice.interceptor.HeaderValidationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final HeaderValidationInterceptor headerValidationInterceptor;

    /**
     * Adds a custom interceptor for header validation and adding localization to the application.
     *
     * @param registry The interceptor registry to register the interceptor.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headerValidationInterceptor)
                .addPathPatterns("/v1/**");
        registry.addInterceptor(new CustomLocaleInterceptor());
    }
}
