package com.github.lucasgms.usermanagement.features.user.domain.interfaces;

import com.github.lucasgms.usermanagement.features.user.domain.dtos.IndividualClientDto;
import com.github.lucasgms.usermanagement.features.user.domain.entities.Client;
import com.github.lucasgms.usermanagement.features.user.domain.entities.User;

public interface IClientService extends IBaseService<Client> {
    Client createIndividualClient(IndividualClientDto dto, User user);
}
