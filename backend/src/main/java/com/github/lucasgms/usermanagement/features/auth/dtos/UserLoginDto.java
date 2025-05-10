package com.github.lucasgms.usermanagement.features.auth.dtos;

import com.github.lucasgms.usermanagement.features.user.domain.entities.User;

public record UserLoginDto(String username, String password, String keycloakId) {
    public User toEntity() {
        return new User(username, password, keycloakId);
    }
}
