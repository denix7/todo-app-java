package com.todo.app.infrastructure;

import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.PersistentException;

import java.util.ArrayList;

public interface TaskDAO extends CrudRepository<Task> {

    void save(Task task) throws PersistentException;

    void saveList(ArrayList<Task> tasks) throws PersistentException;

    public void update(Task task) throws PersistentException;

    public void delete(Task task) throws PersistentException;

    public Task read(Task task) throws PersistentException;

    public ArrayList<Task> loadTasks() throws PersistentException;

}
