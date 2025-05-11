package com.github.lucasgms.usermanagement.features.user.domain.interfaces;

import org.springframework.data.domain.Page;

public interface IBaseService<T> {
    Page<T> get(int page, int size, String searchTerm);
    T create(T entity);
    T findById(long id);
    T update(T entity);
    void delete(long id);
}
