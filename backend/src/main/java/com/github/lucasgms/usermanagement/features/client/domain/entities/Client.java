package com.github.lucasgms.usermanagement.features.client.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.lucasgms.usermanagement.features.auth.domain.entities.User;
import com.github.lucasgms.usermanagement.features.client.domain.dtos.ClientDto;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "client", uniqueConstraints = {
        @UniqueConstraint(name = "unique_cnpj", columnNames = { "cnpj" }),
        @UniqueConstraint(name = "unique_cpf", columnNames = { "cpf" })
})
public abstract class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String telephone;

    private Instant createdAt;

    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keycloak_id", referencedColumnName = "keycloak_id")
    @JsonIgnore
    private User user;

    public ClientDto toClientDto(Client client) {
        if (client instanceof IndividualClient individualClient) {
            return new ClientDto(
                    client.getId(),
                    client.getCreatedAt(),
                    individualClient.getName(),
                    individualClient.getCpf().getValue()
            );
        }

        if (client instanceof CompanyClient companyClient) {
            return new ClientDto(
                    client.getId(),
                    client.getCreatedAt(),
                    companyClient.getCompanyName(),
                    companyClient.getCnpj().getValue()
            );
        }

        return new ClientDto(
                client.getId(),
                client.getCreatedAt(),
                "Unknown",
                "Unknown"
        );

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
