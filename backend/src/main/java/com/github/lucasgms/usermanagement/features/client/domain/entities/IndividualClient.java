package com.github.lucasgms.usermanagement.features.client.domain.entities;

import com.github.lucasgms.usermanagement.features.client.domain.dtos.IndividualClientDto;
import com.github.lucasgms.usermanagement.features.client.domain.valueObject.Cpf;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
public class IndividualClient extends Client {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Embedded
    @Column(name = "cpf", unique = true)
    private Cpf cpf;

    private LocalDate birthDate;

    public IndividualClient() {}

    public IndividualClient(String name, Cpf cpf, LocalDate birthDate, String telephone) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.setTelephone(telephone);
    }

    public IndividualClientDto toDto() {
        return new IndividualClientDto(
                this.getTelephone(),
                this.getName(),
                this.getCpf(),
                this.getBirthDate()
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public void setCpf(Cpf cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
