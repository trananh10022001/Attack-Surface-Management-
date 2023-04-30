package com.example.asm.configuration;

import com.example.asm.authen.AuthenticatorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
    @Autowired
    private AuthenticatorInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .excludePathPatterns("/sign-in")
                .excludePathPatterns("/sign-up")
                .excludePathPatterns("/resources/**")
                .excludePathPatterns("/admin-template/**");
    }
}
