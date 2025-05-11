package com.github.lucasgms.usermanagement.features.client.domain.dtos;

import com.github.lucasgms.usermanagement.features.client.domain.entities.IndividualClient;
import com.github.lucasgms.usermanagement.features.client.domain.valueObject.Cpf;

import java.time.LocalDate;

public record IndividualClientDto(
        String telephone,
        String name,
        Cpf cpf,
        LocalDate birthDate
) {
    public IndividualClient toEntity() {
        return new IndividualClient(
                name,
                cpf,
                birthDate,
                telephone
        );
    }
}
