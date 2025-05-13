package com.github.lucasgms.usermanagement.features.auth.controller;

import com.github.lucasgms.usermanagement.features.auth.service.AuthService;
import com.github.lucasgms.usermanagement.features.auth.domain.dtos.RefreshTokenDto;
import com.github.lucasgms.usermanagement.features.auth.domain.dtos.UserLoginDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthService service;

    AuthController(AuthService authService) {
        this.service = authService;
    }

    @PostMapping
    public ResponseEntity<RefreshTokenDto> token (@RequestBody UserLoginDto user, HttpServletResponse response) {

        RefreshTokenDto token = service.login(user, response);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenDto> refreshToken(@RequestBody RefreshTokenDto refreshToken, HttpServletResponse response) {

        RefreshTokenDto token = service.refreshToken(refreshToken.refresh_token(), response);

        return ResponseEntity.ok(token);
    }

    @GetMapping("/logout")
    public ResponseEntity<RefreshTokenDto> logout(HttpServletResponse response) {

        service.logout(response);

        return ResponseEntity.noContent().build();
    }
}
