package com.todo.app.dao;

import com.todo.app.entities.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T>{
    public void save(T t);

    public void update(T t);

    public void delete(T t);

    public Task read(T t);

    public List<T> loadTasks();
}