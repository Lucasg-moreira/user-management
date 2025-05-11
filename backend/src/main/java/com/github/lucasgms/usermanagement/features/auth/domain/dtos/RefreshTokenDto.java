package com.github.lucasgms.usermanagement.features.auth.domain.dtos;

import java.time.Instant;

public record RefreshTokenDto(
        String refresh_token,
        String token_type,
        int refresh_expires_in,
        Instant tokenExpiration,
        Instant refreshExpiration
) { }
