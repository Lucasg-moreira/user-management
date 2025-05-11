package com.github.lucasgms.usermanagement.features.auth.service;

import com.github.lucasgms.usermanagement.exception.BusinessException;
import com.github.lucasgms.usermanagement.features.auth.repository.UserRepository;
import com.github.lucasgms.usermanagement.features.auth.domain.entities.User;
import com.github.lucasgms.usermanagement.features.auth.domain.interfaces.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<User> get(int page, int size, String searchTerm) {
        Pageable pageable = PageRequest.of(page, size);

        var result = repository.findAll(pageable);

        return result;
    }

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public User findById(long id) {
        return repository.findById(id).stream()
                .findFirst()
                .get();
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }


    @Override
    public User findByKeycloakId(String username, boolean requiredUser) {
        var user = repository.findByKeycloakId(username);

        if (user == null && requiredUser) {
            throw new BusinessException("Usuário não encontrado!");
        }

        return user;
    }
}
