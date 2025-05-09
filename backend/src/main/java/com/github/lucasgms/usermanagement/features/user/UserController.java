package com.github.lucasgms.usermanagement.features.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public String get() {
        return "Hello";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String find() {
        return "admin";
    }
}
