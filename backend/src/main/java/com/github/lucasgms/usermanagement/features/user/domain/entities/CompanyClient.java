package com.github.lucasgms.usermanagement.features.user.domain.entities;

import jakarta.persistence.Entity;

@Entity
public class CompanyClient extends Client {
    private String companyName;
    private String fantasyName;
    private String cnpj;

    public CompanyClient(String companyName, String cnpj, String fantasyName) {
        this.companyName = companyName;
        this.cnpj = cnpj;
        this.fantasyName = fantasyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

}
