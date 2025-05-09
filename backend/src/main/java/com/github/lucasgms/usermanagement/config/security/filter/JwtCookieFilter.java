package com.github.lucasgms.usermanagement.config.security.filter;

import com.github.lucasgms.usermanagement.config.security.JWTConverter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtCookieFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final JWTConverter jwtConverter;

    public JwtCookieFilter(JwtDecoder jwtDecoder, JWTConverter jwtConverter) {
        this.jwtDecoder = jwtDecoder;
        this.jwtConverter = jwtConverter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userToken".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    Jwt jwt = jwtDecoder.decode(token);
                    AbstractAuthenticationToken authentication = jwtConverter.convert(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    break;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}

