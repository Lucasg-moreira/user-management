package com.github.lucasgms.usermanagement;

import com.github.lucasgms.usermanagement.features.auth.domain.entities.User;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.lucasgms.usermanagement.features.auth.service.UserService;
import com.github.lucasgms.usermanagement.features.client.domain.dtos.CompanyClientDto;
import com.github.lucasgms.usermanagement.features.client.domain.entities.Client;
import com.github.lucasgms.usermanagement.features.client.domain.entities.CompanyClient;
import com.github.lucasgms.usermanagement.features.client.domain.entities.IndividualClient;
import com.github.lucasgms.usermanagement.features.client.domain.valueObject.Cnpj;
import com.github.lucasgms.usermanagement.features.client.domain.valueObject.Cpf;
import com.github.lucasgms.usermanagement.features.client.repository.ClientRepository;
import com.github.lucasgms.usermanagement.features.client.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository repository;

    @Mock
    private UserService userService;


    @Test
    void testGetClient() {
        Cnpj cnpj1 = new Cnpj("40227598000170");
        Client client1 = new CompanyClient("top", cnpj1, "empresa legal", "12312312312");

        Cpf cpf = new Cpf("70333520181");
        LocalDate birthDate = LocalDate.of(1990, 12, 12);
        Client client2 = new IndividualClient("empresa legal", cpf, birthDate, "5555555");

        Page<Client> page = new PageImpl<>(java.util.List.of(client1, client2));
        when(repository.findAll(PageRequest.of(0, 2))).thenReturn(page);


        var result = clientService.findAllClients(0, 2);

        assert(result.getTotalElements() == 2);
        assert(result.getContent().size() == 2);

        verify(repository, times(1)).findAll(PageRequest.of(0, 2));
    }

    @Test
    void testCreateClient() {
        User userLogged = new User("lucasgms", "123456");

        userLogged.setKeycloak_id("123456");

        Cnpj cnpj1 = new Cnpj("40227598000170");
        Client savedClient = new CompanyClient("top", cnpj1, "empresa legal", "12312312312");

        savedClient.setCreatedAt(Instant.now());
        savedClient.setUser(userLogged);

        var companyClient = new CompanyClientDto("empresa top", "LTDA", cnpj1, "12312312312");

        when(repository.save(any(Client.class))).thenReturn(savedClient);
        when(userService.findByKeycloakId(userLogged.getKeycloak_id(), true)).thenReturn(userLogged);

        var result = clientService.createCompanyClient(companyClient, userLogged);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getUser());
    }
}
