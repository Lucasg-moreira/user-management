package com.github.lucasgms.usermanagement.features.user.entities;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class CompanyClient extends Client {
    private String name;
    private String cnpj;
    private LocalDate tradeName;

    public CompanyClient(String name, String cnpj, LocalDate tradeName) {
        this.name = name;
        this.cnpj = cnpj;
        this.tradeName = tradeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public LocalDate getTradeName() {
        return tradeName;
    }

    public void setTradeName(LocalDate tradeName) {
        this.tradeName = tradeName;
    }
}
