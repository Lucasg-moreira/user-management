package com.github.lucasgms.usermanagement.features.user.domain.entities;

import com.github.lucasgms.usermanagement.features.user.domain.dtos.CompanyClientDto;
import com.github.lucasgms.usermanagement.features.user.domain.valueObject.Cnpj;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

@Entity
public class CompanyClient extends Client {
    private String companyName;
    private String fantasyName;

    @Embedded
    @Column(name = "cnpj", unique = true)
    private Cnpj cnpj;

    public CompanyClient() {}

    public CompanyClient(String companyName, Cnpj cnpj, String fantasyName, String telephone) {
        this.companyName = companyName;
        this.cnpj = cnpj;
        this.fantasyName = fantasyName;

        this.setTelephone(telephone);
    }

    public CompanyClientDto toDto() {
        return new CompanyClientDto(
                this.getCompanyName(),
                this.getFantasyName(),
                this.getCnpj(),
                this.getTelephone()
        );
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Cnpj getCnpj() {
        return cnpj;
    }

    public void setCnpj(Cnpj cnpj) {
        this.cnpj = cnpj;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

}
