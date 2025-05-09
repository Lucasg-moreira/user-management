package com.github.lucasgms.usermanagement.features.user.domain.entities;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class IndividualClient extends Client {
    private String name;
    private String cpf;
    private LocalDate birthDate;

    public IndividualClient(String name, String cpf, LocalDate birthDate) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
