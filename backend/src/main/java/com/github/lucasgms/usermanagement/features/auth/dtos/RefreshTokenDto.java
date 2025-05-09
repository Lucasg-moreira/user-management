package com.github.lucasgms.usermanagement.features.auth.dtos;

public record RefreshTokenDto(
        String refresh_token,
        String token_type,
        int refresh_expires_in
) { }
