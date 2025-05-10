package com.github.lucasgms.usermanagement.features.auth;

import com.github.lucasgms.usermanagement.exception.BusinessException;
import com.github.lucasgms.usermanagement.features.auth.dtos.TokenDto;
import com.github.lucasgms.usermanagement.features.auth.dtos.UserLoginDto;
import com.github.lucasgms.usermanagement.features.user.domain.entities.User;
import com.github.lucasgms.usermanagement.features.user.domain.interfaces.IUserService;
import com.github.lucasgms.usermanagement.shared.BaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService extends BaseService {
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

    public TokenDto login(UserLoginDto user) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate rt = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = getCredentialsKeyCloak(user);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        ResponseEntity<TokenDto> result = rt.postForEntity(keycloakUrl + "/protocol/openid-connect/token", entity, TokenDto.class);

        validateKeycloakApiResponse(result);

        createUser(user, result);

        return result.getBody();
    }

    private void createUser(UserLoginDto user, ResponseEntity<TokenDto> result) {
        if (!result.getStatusCode().is2xxSuccessful())
            return;

        User entity = userService.findByUsername(user.username());

        if (entity == null) {
            String hashedPassword = hashsPassword(user.password());

            Jwt jwt = jwtDecoder.decode(result.getBody().access_token());
            String keycloakId = jwt.getClaims().get("sub").toString();

            UserLoginDto newDto = new UserLoginDto(user.username(), hashedPassword, keycloakId);

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

    public TokenDto refreshToken(String refreshToken) {
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

        return result.getBody();
    }

    private static void validateKeycloakApiResponse(ResponseEntity<TokenDto> result) {
        if (result == null || result.getStatusCode().is4xxClientError() || result.getStatusCode().is5xxServerError()) {
            throw new BusinessException("Ocorreu um erro ao logar.");
        }
    }
}
