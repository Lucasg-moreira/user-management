package com.github.lucasgms.usermanagement.features.user.repository;

import com.github.lucasgms.usermanagement.features.user.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
