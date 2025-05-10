package com.github.lucasgms.usermanagement.features.user.controller;

import com.github.lucasgms.usermanagement.features.user.domain.entities.Client;
import com.github.lucasgms.usermanagement.features.user.domain.entities.User;
import com.github.lucasgms.usermanagement.features.user.domain.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    IUserService service;

    UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<User>> get(
            @Valid
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam() String searchTerm
    ) {
        return ResponseEntity.ok(this.service.get(page, size, searchTerm));
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> post(
            @Valid
            @RequestBody User user
            ) {
        return ResponseEntity.ok(service.create(user));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String find() {
        return "admin";
    }
}
