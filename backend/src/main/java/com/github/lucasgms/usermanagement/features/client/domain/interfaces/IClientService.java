package com.github.lucasgms.usermanagement.features.client.domain.interfaces;

import com.github.lucasgms.usermanagement.features.client.domain.dtos.ClientDto;
import com.github.lucasgms.usermanagement.features.client.domain.dtos.CompanyClientDto;
import com.github.lucasgms.usermanagement.features.client.domain.dtos.FilterParamsDto;
import com.github.lucasgms.usermanagement.features.client.domain.dtos.IndividualClientDto;

import com.github.lucasgms.usermanagement.features.client.domain.entities.Client;
import com.github.lucasgms.usermanagement.features.auth.domain.entities.User;
import org.springframework.data.domain.Page;

public interface IClientService extends IBaseService<Client> {
    Client createIndividualClient(IndividualClientDto dto, User userLogged);
    Client createCompanyClient(CompanyClientDto dto, User userLogged);

    Page<ClientDto> findAllClients(int page, int size);
    Page<ClientDto> findAllClientsByFilter(int page, int size, FilterParamsDto filterParamsDto);


    IndividualClientDto updateIndividualClient(IndividualClientDto dto, long id);
    CompanyClientDto updateCompanyClient(CompanyClientDto dto, long id);

}
