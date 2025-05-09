package com.github.lucasgms.usermanagement.features.user;

import com.github.lucasgms.usermanagement.features.user.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
}
