package com.todo.app.infrastructure;

import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.PersistentException;

import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T>{
    public void save(T t);

    public void update(T t);

    public void delete(T t);

    public T read(int index);

    public List<T> loadTasks();
}