package com.shadyplace.springweb.repository;

import java.util.List;

public abstract class DAO<E> {
    public abstract List<E> getAll();
    public abstract E find(long id);
    public abstract void create(E entity);
    public abstract boolean update(long id, E entity);
    public abstract void delete(E entity);
}
