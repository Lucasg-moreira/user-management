package com.github.lucasgms.usermanagement.features.client.domain.dtos;

import java.time.LocalDate;

public record FilterParamsDto(
        LocalDate createdAt,
        String name,
        String cpfCnpj
) {
}
