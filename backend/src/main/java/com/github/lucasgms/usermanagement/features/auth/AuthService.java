package com.github.lucasgms.usermanagement.features.auth;

import com.github.lucasgms.usermanagement.exception.BusinessException;
import com.github.lucasgms.usermanagement.features.auth.dtos.TokenDto;
import com.github.lucasgms.usermanagement.features.auth.dtos.UserLoginDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {
    @Value("${keycloak.grant-type}")
    private String grantType;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String keycloakUrl;

    public TokenDto login(UserLoginDTO user) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate rt = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("username", user.username());
        formData.add("password", user.password());
        formData.add("grant_type", grantType);
        formData.add("client_id", clientId);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        ResponseEntity<TokenDto> result = rt.postForEntity(keycloakUrl + "/protocol/openid-connect/token", entity, TokenDto.class);

        if (result.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            throw new BusinessException("Verifique o usuário e senha");
        }

        return result.getBody();
    }
}
