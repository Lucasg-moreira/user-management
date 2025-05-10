package com.github.lucasgms.usermanagement.features.user.service;

import com.github.lucasgms.usermanagement.exception.BusinessException;
import com.github.lucasgms.usermanagement.features.user.domain.dtos.CompanyClientDto;
import com.github.lucasgms.usermanagement.features.user.domain.dtos.IndividualClientDto;
import com.github.lucasgms.usermanagement.features.user.domain.entities.Client;
import com.github.lucasgms.usermanagement.features.user.domain.entities.CompanyClient;
import com.github.lucasgms.usermanagement.features.user.domain.entities.IndividualClient;
import com.github.lucasgms.usermanagement.features.user.domain.entities.User;
import com.github.lucasgms.usermanagement.features.user.domain.interfaces.IClientService;
import com.github.lucasgms.usermanagement.features.user.domain.interfaces.IUserService;
import com.github.lucasgms.usermanagement.features.user.repository.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.Instant;

@Service
public class ClientService implements IClientService {
    ClientRepository repository;

    IUserService userService;

    public ClientService(
            ClientRepository repository,
            IUserService userService
    ) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public Page<Client> get(int page, int size, String searchTerm) {
        Pageable pageable = PageRequest.of(page, size);

        return repository.findAll(pageable);
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

        User user = userService.findByKeycloakId(userLogged.getKeycloak_id(), true);

        entity.setUser(user);

        return repository.save(entity);
    }

    @Override
    public Client createCompanyClient(CompanyClientDto dto, User userLogged) {
        CompanyClient entity = dto.toEntity();

        entity.setCreatedAt(Instant.now());

        User user = userService.findByKeycloakId(userLogged.getKeycloak_id(), true);

        entity.setUser(user);

        return repository.save(entity);
    }

    @Override
    public IndividualClientDto updateIndividualClient(IndividualClientDto dto, long id) {
        IndividualClient entity = (IndividualClient) repository.findById(id).orElseThrow(
                () -> new BusinessException("Erro ao atualizar o registro.")
        );

        entity.setUpdatedAt(Instant.now());

        entity.setCpf(dto.cpf());
        entity.setName(dto.name());
        entity.setTelephone(dto.telephone());

        IndividualClient updatedEntity = repository.save(entity);

        return updatedEntity.toDto();
    }

    @Override
    public CompanyClientDto updateCompanyClient(CompanyClientDto dto, long id) {
        return null;
    }
}
