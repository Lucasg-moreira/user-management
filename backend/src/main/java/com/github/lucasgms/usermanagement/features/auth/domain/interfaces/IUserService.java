package com.github.lucasgms.usermanagement.features.auth.domain.interfaces;

import com.github.lucasgms.usermanagement.features.auth.domain.entities.User;
import com.github.lucasgms.usermanagement.features.client.domain.interfaces.IBaseService;

public interface IUserService extends IBaseService<User> {
    User findByUsername(String name);

    User findByKeycloakId(String keycloakId, boolean requiredUser);
}
