package com.github.lucasgms.usermanagement.features.user.repository;

import com.github.lucasgms.usermanagement.features.user.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
