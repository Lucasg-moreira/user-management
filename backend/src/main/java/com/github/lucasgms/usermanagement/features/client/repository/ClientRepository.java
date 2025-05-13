package com.github.lucasgms.usermanagement.features.client.repository;

import com.github.lucasgms.usermanagement.features.client.domain.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE " +
            "(:name IS NULL OR LOWER(c.name) LIKE LOWER(concat('%', :name, '%')) OR LOWER(c.companyName) LIKE LOWER(concat('%', :name, '%'))) AND " +
            "(:cpfCnpj IS NULL OR (c.cpf.value LIKE concat('%', :cpfCnpj, '%') OR c.cnpj.value LIKE concat('%', :cpfCnpj, '%'))) AND " +
            "(:createdAt IS NULL OR FORMATDATETIME(c.createdAt, 'yyyy-MM-dd') = FORMATDATETIME(:createdAt, 'yyyy-MM-dd'))")
    Page<Client> findByFilters(String name, String cpfCnpj, Instant createdAt, Pageable pageable);
}

