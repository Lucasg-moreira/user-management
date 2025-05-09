package com.github.lucasgms.usermanagement.features.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.lucasgms.usermanagement.exception.BusinessException;
import com.github.lucasgms.usermanagement.features.auth.dtos.TokenDto;
import com.github.lucasgms.usermanagement.features.auth.dtos.UserLoginDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.antlr.v4.runtime.Token;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthService service;

    AuthController(AuthService authService) {
        this.service = authService;
    }

    @PostMapping
    public ResponseEntity<TokenDto> token (@RequestBody UserLoginDTO user, HttpServletResponse response) {

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

        return ResponseEntity.ok(result);
    }
}
