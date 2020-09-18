package com.todo.app.dao;

import java.util.Optional;

public interface ICrudRepository <T extends Object, ID extends Object>{
    public <S extends T> S save(S s);

    public <S extends T> Iterable<S> saveAll(Iterable<S> iterable);

    public Optional<T> findById(ID id);

    public boolean existsById(ID id);

    public Iterable<T> findAll();

    public long count();

    public void deleteById(ID id);

    public void delete(T t);
}