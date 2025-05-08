package com.github.lucasgms.usermanagement.features.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<String> login(UserLoginDTO user) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate rt = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("username", user.username());
        formData.add("password", user.password());
        formData.add("grant_type", grantType);
        formData.add("client_id", clientId);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(formData, headers);

        return rt.postForEntity(keycloakUrl + "/protocol/openid-connect/token", entity, String.class);
    }
}
