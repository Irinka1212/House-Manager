package com.housemanager.services;

import com.housemanager.exceptions.EntityCreationException;
import com.housemanager.exceptions.EntityNotFoundException;
import com.housemanager.exceptions.EntityUpdateException;
import com.housemanager.repositories.BaseRepository;

import java.util.List;
import java.util.function.Function;

public class BaseService<T> {

    protected final BaseRepository<T> repository;
    private final Function<Long, String> notFoundMessageSupplier;
    private final String entityName;

    public BaseService(BaseRepository<T> repository, Function<Long, String> notFoundMessageSupplier, String entityName) {
        this.repository = repository;
        this.notFoundMessageSupplier = notFoundMessageSupplier;
        this.entityName = entityName;
    }

    public void create(T entity) throws EntityCreationException {
        if (entity == null) {
            throw new EntityCreationException(entityName);
        }
        repository.save(entity);
    }

    public T get(Long id) throws EntityNotFoundException {
        T entity = repository.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException(notFoundMessageSupplier.apply(id));
        }
        return entity;
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public void update(T entity) throws EntityUpdateException {
        if (entity == null) {
            throw new EntityUpdateException(entityName);
        }
        repository.update(entity);
    }

    public void delete(Long id) throws EntityNotFoundException {
        T entity = repository.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException(notFoundMessageSupplier.apply(id));
        }
        repository.delete(entity);
    }
}