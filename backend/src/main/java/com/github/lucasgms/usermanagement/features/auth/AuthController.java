package com.github.lucasgms.usermanagement.features.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.lucasgms.usermanagement.exception.BusinessException;
import com.github.lucasgms.usermanagement.features.auth.dtos.RefreshTokenDto;
import com.github.lucasgms.usermanagement.features.auth.dtos.TokenDto;
import com.github.lucasgms.usermanagement.features.auth.dtos.UserLoginDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthService service;


    AuthController(AuthService authService) {
        this.service = authService;
    }

    @PostMapping
    public ResponseEntity<RefreshTokenDto> token (@RequestBody UserLoginDTO user, HttpServletResponse response) {

        var result = service.login(user);

        if (result == null) {
            throw new BusinessException("Usuário não autenticado.");
        }

        int duration = 300;

        Cookie cookie = new Cookie("userToken", result.access_token());

        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(duration);

        response.addCookie(cookie);

        return ResponseEntity.ok(result.toRefreshToken());
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenDto> refreshToken(@RequestBody Map<String, String> refreshToken, HttpServletResponse response) {

        TokenDto token = service.refreshToken(refreshToken.get("refresh_token"));

        Cookie cookie = new Cookie("userToken", token.access_token());

        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(300);

        response.addCookie(cookie);

        return ResponseEntity.ok(token.toRefreshToken());
    }
}
