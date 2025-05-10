package com.github.lucasgms.usermanagement.features.user.domain.interfaces;

import com.github.lucasgms.usermanagement.features.user.domain.entities.User;

public interface IUserService extends IBaseService<User>{
    User findByUsername(String name);

    User findByKeycloakId(String keycloakId);
}
