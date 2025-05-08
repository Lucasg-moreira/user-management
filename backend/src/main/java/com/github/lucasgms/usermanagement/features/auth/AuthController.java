package com.github.lucasgms.usermanagement.features.auth;

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
    public ResponseEntity<String> token (@RequestBody UserLoginDTO user) {
        return service.login(user);
    }
}
