package com.github.lucasgms.usermanagement.features.user.domain.entities;

import com.github.lucasgms.usermanagement.features.user.domain.valueObject.Cpf;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class IndividualClient extends Client {
    private String name;
    @Embedded
    private Cpf cpf;
    private LocalDate birthDate;

    public IndividualClient(String name, Cpf cpf, LocalDate birthDate, String telephone) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.setTelephone(telephone);
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
