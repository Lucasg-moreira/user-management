package com.github.lucasgms.usermanagement.config.security;

import com.github.lucasgms.usermanagement.shared.interceptor.CookieInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RequestInterceptorConfig implements WebMvcConfigurer {
    JwtDecoder jwtDecoder;
    JWTConverter jwtConverter;

    RequestInterceptorConfig(JwtDecoder jwtDecoder, JWTConverter jwtConverter) {
        this.jwtDecoder = jwtDecoder;
        this.jwtConverter = jwtConverter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CookieInterceptor(jwtDecoder, jwtConverter));
    }
}
