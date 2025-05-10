package com.github.lucasgms.usermanagement.features.user.service;

import com.github.lucasgms.usermanagement.features.user.domain.dtos.CompanyClientDto;
import com.github.lucasgms.usermanagement.features.user.domain.dtos.IndividualClientDto;
import com.github.lucasgms.usermanagement.features.user.domain.entities.Client;
import com.github.lucasgms.usermanagement.features.user.domain.entities.CompanyClient;
import com.github.lucasgms.usermanagement.features.user.domain.entities.IndividualClient;
import com.github.lucasgms.usermanagement.features.user.domain.entities.User;
import com.github.lucasgms.usermanagement.features.user.domain.interfaces.IClientService;
import com.github.lucasgms.usermanagement.features.user.repository.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import java.time.Instant;

@Service
public class ClientService implements IClientService {
    ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Client> get(int page, int size, String searchTerm) {
        return null;
    }

    @Override
    public Client create(Client entity) {
        return null;
    }

    @Override
    public Client findById(long id) {
        return null;
    }

    @Override
    public Client update(Client entity) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Client createIndividualClient(IndividualClientDto dto, User userLogged) {
        IndividualClient entity = dto.toEntity();

        entity.setCreatedAt(Instant.now());
        entity.setUser(userLogged);

        return repository.save(entity);
    }

    @Override
    public Client createCompanyClient(CompanyClientDto dto, User userLogged) {
        CompanyClient entity = dto.toEntity();

        entity.setCreatedAt(Instant.now());
        entity.setUser(userLogged);

        return repository.save(entity);
    }
}
