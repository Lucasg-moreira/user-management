package com.github.lucasgms.usermanagement.features.client.repository;

import com.github.lucasgms.usermanagement.features.client.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
