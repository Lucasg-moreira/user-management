package com.github.lucasgms.usermanagement.features.user.domain.dtos;

import com.github.lucasgms.usermanagement.features.user.domain.entities.Client;
import com.github.lucasgms.usermanagement.features.user.domain.entities.IndividualClient;
import com.github.lucasgms.usermanagement.features.user.domain.valueObject.Cpf;

import javax.swing.text.html.parser.Entity;
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
