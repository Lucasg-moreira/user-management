package com.github.lucasgms.usermanagement.features.auth;

import com.github.lucasgms.usermanagement.features.auth.dtos.RefreshTokenDto;
import com.github.lucasgms.usermanagement.features.auth.dtos.UserLoginDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RefreshTokenDto> token (@RequestBody UserLoginDto user, HttpServletResponse response) {

        RefreshTokenDto token = service.login(user, response);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenDto> refreshToken(@RequestBody Map<String, String> refreshToken, HttpServletResponse response) {

        RefreshTokenDto token = service.refreshToken(refreshToken.get("refresh_token"), response);

        return ResponseEntity.ok(token);
    }
}
