package com.github.lucasgms.usermanagement.features.client.controller;

import com.github.lucasgms.usermanagement.features.client.domain.dtos.ClientDto;
import com.github.lucasgms.usermanagement.features.client.domain.dtos.CompanyClientDto;
import com.github.lucasgms.usermanagement.features.client.domain.dtos.IndividualClientDto;
import com.github.lucasgms.usermanagement.features.client.domain.entities.Client;
import com.github.lucasgms.usermanagement.features.auth.domain.entities.User;
import com.github.lucasgms.usermanagement.features.client.domain.interfaces.IClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
     IClientService service;

    ClientController(IClientService service) {
        this.service = service;
    }

    @PostMapping("/individual")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> createIndividualClient(
            @Valid
            HttpServletRequest request,
            @RequestBody IndividualClientDto dto) {

        if (request.getAttribute("user") == null)
            return ResponseEntity.badRequest().build();

        User userLogged = (User) request.getAttribute("user");

        return ResponseEntity.ok(this.service.createIndividualClient(dto, userLogged));
    }

    @PostMapping("/company")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> createCompanyClient(
            HttpServletRequest request,
            @RequestBody CompanyClientDto client
    ) {
        if (request.getAttribute("user") == null)
            return ResponseEntity.badRequest().build();

        User userLogged = (User) request.getAttribute("user");

        return ResponseEntity.ok(this.service.createCompanyClient(client, userLogged));
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<ClientDto>> get(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam() String searchTerm
    ) {
        return ResponseEntity.ok(this.service.findAllClients(page, size, searchTerm));
    }

    @PutMapping("/individual/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<IndividualClientDto> updateIndividualClient(
            @Valid
            @RequestBody IndividualClientDto dto,
            @PathVariable("id") long id
    ) {
        return ResponseEntity.ok(this.service.updateIndividualClient(dto, id));
    }

    @PutMapping("/company/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyClientDto> updateCompanyClient(
            @Valid
            @RequestBody CompanyClientDto dto,
            @PathVariable("id") long id
    ) {
        return ResponseEntity.ok(this.service.updateCompanyClient(dto, id));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") long id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

}


