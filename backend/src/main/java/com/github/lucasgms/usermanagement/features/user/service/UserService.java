package com.github.lucasgms.usermanagement.features.user.service;

import com.github.lucasgms.usermanagement.features.user.domain.entities.User;
import com.github.lucasgms.usermanagement.features.user.domain.interfaces.IUserService;
import com.github.lucasgms.usermanagement.features.user.repository.UserRepository;
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
}
