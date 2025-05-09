package com.github.lucasgms.usermanagement.features.auth.dtos;

public record TokenDto(
        String access_token,
        String refresh_token,
        String token_type,
        String session_state,
        String scope,
        int expires_in,
        int refresh_expires_in,
        int not_before_policy
) {
    public RefreshTokenDto toRefreshToken() {
        return new RefreshTokenDto(refresh_token, token_type, refresh_expires_in);
    }
}



