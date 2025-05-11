package com.github.lucasgms.usermanagement.shared.interceptor;

import com.github.lucasgms.usermanagement.config.security.JWTConverter;
import com.github.lucasgms.usermanagement.features.user.domain.entities.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CookieInterceptor implements HandlerInterceptor {
    JwtDecoder jwtDecoder;
    JWTConverter jwtConverter;

    public CookieInterceptor() {}

    public CookieInterceptor(JwtDecoder jwtDecoder, JWTConverter jwtConverter) {
        this.jwtDecoder = jwtDecoder;
        this.jwtConverter = jwtConverter;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            Cookie[] cookies = request.getCookies();

            if (cookies == null) {
                return true;
            }

            for (Cookie cookie : cookies) {
                if ("userToken".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    Jwt jwt = jwtDecoder.decode(token);

                    User userCookie = new User(jwt.getClaims().get("name").toString(),null, jwt.getClaims().get("sub").toString());
                    request.setAttribute("user", userCookie);
                    break;
                }
            }
            return true;
    }
}



