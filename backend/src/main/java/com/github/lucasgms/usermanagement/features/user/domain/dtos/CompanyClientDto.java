package com.github.lucasgms.usermanagement.features.user.domain.dtos;

import com.github.lucasgms.usermanagement.features.user.domain.entities.CompanyClient;
import com.github.lucasgms.usermanagement.features.user.domain.valueObject.Cnpj;

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
