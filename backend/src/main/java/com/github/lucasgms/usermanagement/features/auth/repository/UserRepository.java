package com.github.lucasgms.usermanagement.features.auth.repository;

import com.github.lucasgms.usermanagement.features.auth.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByKeycloakId(String keycloakId);
}
