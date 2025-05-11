package com.github.lucasgms.usermanagement.features.client.domain.dtos;

import java.time.Instant;

public record ClientDto(
        long id,
        Instant createdDate,
        String namePersonOrCompany,
        String cpfCnpj
) {
}
