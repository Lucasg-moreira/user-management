package com.github.lucasgms.usermanagement.features.client.domain.interfaces;

import com.github.lucasgms.usermanagement.features.client.domain.dtos.CompanyClientDto;
import com.github.lucasgms.usermanagement.features.client.domain.dtos.IndividualClientDto;

import com.github.lucasgms.usermanagement.features.client.domain.entities.Client;
import com.github.lucasgms.usermanagement.features.auth.domain.entities.User;

public interface IClientService extends IBaseService<Client> {
    Client createIndividualClient(IndividualClientDto dto, User userLogged);
    Client createCompanyClient(CompanyClientDto dto, User userLogged);

    IndividualClientDto updateIndividualClient(IndividualClientDto dto, long id);
    CompanyClientDto updateCompanyClient(CompanyClientDto dto, long id);
}
