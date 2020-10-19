package com.todo.app.infrastructure;

import java.util.List;
import java.util.UUID;

public interface CrudRepository<T>{
    public void save(T t);

    public void update(UUID id, T t);

    public boolean delete(UUID id);

    public T read(UUID id);

    public List<T> loadTasks();
}