package com.github.lucasgms.usermanagement.features.auth;

import com.github.lucasgms.usermanagement.exception.BusinessException;
import com.github.lucasgms.usermanagement.features.auth.dtos.RefreshTokenDto;
import com.github.lucasgms.usermanagement.features.auth.dtos.TokenDto;
import com.github.lucasgms.usermanagement.features.auth.dtos.UserLoginDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthService service;

    AuthController(AuthService authService) {
        this.service = authService;
    }

    @PostMapping
    public ResponseEntity<RefreshTokenDto> token (@RequestBody UserLoginDto user, HttpServletResponse response) {

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
