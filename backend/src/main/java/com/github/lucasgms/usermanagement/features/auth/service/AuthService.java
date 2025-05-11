package com.github.lucasgms.usermanagement.features.auth.service;

import com.github.lucasgms.usermanagement.exception.BusinessException;
import com.github.lucasgms.usermanagement.features.auth.domain.entities.User;
import com.github.lucasgms.usermanagement.features.auth.domain.dtos.RefreshTokenDto;
import com.github.lucasgms.usermanagement.features.auth.domain.dtos.TokenDto;
import com.github.lucasgms.usermanagement.features.auth.domain.dtos.UserLoginDto;
import com.github.lucasgms.usermanagement.features.auth.domain.interfaces.IUserService;
import com.github.lucasgms.usermanagement.shared.utils.TokenUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
public class AuthService {
    @Value("${keycloak.grant-type}")
    private String grantType;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String keycloakUrl;

    private JwtDecoder jwtDecoder;

    private IUserService userService;

    public AuthService(JwtDecoder jwtDecoder, IUserService userService) {
        super();
        this.jwtDecoder = jwtDecoder;
        this.userService = userService;
    }

    public RefreshTokenDto login(UserLoginDto user, HttpServletResponse response) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = getCredentialsKeyCloak(user);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        ResponseEntity<TokenDto> result = rt.postForEntity(keycloakUrl + "/protocol/openid-connect/token", entity, TokenDto.class);

        validateKeycloakApiResponse(result);

        createUser(user, result);

        setCookie(response, result);

        int fourMinutes = 240;
        int thirtyMinutes = 1800;

        Instant accessTokenExpiredDate = TokenUtils.createExpiredDateToken(fourMinutes);
        Instant refreshTokenExpiredDate = TokenUtils.createExpiredDateToken(thirtyMinutes);

        return result.getBody().toRefreshToken(accessTokenExpiredDate, refreshTokenExpiredDate);
    }

    private void createUser(UserLoginDto user, ResponseEntity<TokenDto> result) {
        if (!result.getStatusCode().is2xxSuccessful())
            return;

        Jwt jwt = jwtDecoder.decode(result.getBody().access_token());

        String keycloakId = jwt.getClaims().get("sub").toString();

        String hashedPassword = hashsPassword(user.password());

        UserLoginDto newDto = new UserLoginDto(user.username(), hashedPassword, keycloakId);

        User entity = userService.findByKeycloakId(keycloakId, false);

        if (entity == null) {
            userService.create(newDto.toEntity());
        }
    }

    String hashsPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private MultiValueMap<String, String> getCredentialsKeyCloak(UserLoginDto user) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("username", user.username());
        formData.add("password", user.password());
        formData.add("grant_type", grantType);
        formData.add("client_id", clientId);

        return formData;
    }

    public RefreshTokenDto refreshToken(String refreshToken, HttpServletResponse response) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("grant_type", "refresh_token");
        formData.add("refresh_token", refreshToken);
        formData.add("client_id", clientId);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        ResponseEntity<TokenDto> result = rt.postForEntity(keycloakUrl + "/protocol/openid-connect/token", entity, TokenDto.class);

        validateKeycloakApiResponse(result);

        setCookie(response, result);

        int fourMinutes = 240;
        int thirtyMinutes = 1800;

        Instant accessTokenExpiredDate = TokenUtils.createExpiredDateToken(fourMinutes);
        Instant refreshTokenExpiredDate = TokenUtils.createExpiredDateToken(thirtyMinutes);

        return result.getBody().toRefreshToken(accessTokenExpiredDate, refreshTokenExpiredDate);
    }

    private void setCookie(HttpServletResponse response, ResponseEntity<TokenDto> result) {
        Cookie cookie = new Cookie("userToken", result.getBody().access_token());

        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(300);

        response.addCookie(cookie);
    }

    private static void validateKeycloakApiResponse(ResponseEntity<TokenDto> result) {
        if (result == null || result.getStatusCode().is4xxClientError() || result.getStatusCode().is5xxServerError()) {
            throw new BusinessException("Ocorreu um erro ao logar.");
        }
    }
}
