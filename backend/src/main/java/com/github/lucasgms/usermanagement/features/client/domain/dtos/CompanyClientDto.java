package com.github.lucasgms.usermanagement.features.client.domain.dtos;

import com.github.lucasgms.usermanagement.features.client.domain.entities.CompanyClient;
import com.github.lucasgms.usermanagement.features.client.domain.valueObject.Cnpj;

public record CompanyClientDto(
        String companyName,
        String fantasyName,
        Cnpj cnpj,
        String telephone
) {
    public CompanyClient toEntity() {
        return new CompanyClient(
                companyName,
                cnpj,
                fantasyName,
                telephone
        );
    }
}
