package com.todo.app.infrastructure;

import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.PersistentException;

import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T>{
    public void save(T t) throws PersistentException;

    public void update(T t) throws PersistentException;

    public void delete(T t) throws PersistentException;

    public Task read(T t) throws PersistentException;

    public List<T> loadTasks() throws PersistentException;
}