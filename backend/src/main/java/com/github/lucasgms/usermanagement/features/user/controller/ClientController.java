package com.github.lucasgms.usermanagement.features.user.controller;

import com.github.lucasgms.usermanagement.features.user.domain.dtos.IndividualClientDto;
import com.github.lucasgms.usermanagement.features.user.domain.entities.Client;
import com.github.lucasgms.usermanagement.features.user.domain.entities.CompanyClient;
import com.github.lucasgms.usermanagement.features.user.domain.entities.IndividualClient;
import com.github.lucasgms.usermanagement.features.user.domain.entities.User;
import com.github.lucasgms.usermanagement.features.user.domain.interfaces.IClientService;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<Client>> get(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam() String searchTerm
    ) {
        return ResponseEntity.ok(this.service.get(page, size, searchTerm));
    }

    @PostMapping("/individual")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> createIndividualClient(
            HttpServletRequest request,
            @RequestBody IndividualClientDto client) {

        if (request.getAttribute("user") == null)
            return ResponseEntity.badRequest().build();

        User userLogged = (User) request.getAttribute("user");

        return ResponseEntity.ok(this.service.createIndividualClient(client, userLogged));
    }

    @PostMapping("/company")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> createCompanyClient(
            @RequestBody CompanyClient client
    ) {
        return ResponseEntity.ok(this.service.create(client));
    }
}


