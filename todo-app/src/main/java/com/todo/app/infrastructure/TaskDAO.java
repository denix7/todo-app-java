package com.todo.app.infrastructure;

import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.PersistentException;

import java.util.ArrayList;

public interface TaskDAO extends CrudRepository<Task> {

    void save(Task task);

    void saveList(ArrayList<Task> tasks);

    public void update(Task task);

    public void delete(Task task);

    public Task read(int index);

    public ArrayList<Task> loadTasks();

}
