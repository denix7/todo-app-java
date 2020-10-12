package com.todo.app.infrastructure;

import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.PersistentException;

import java.util.ArrayList;
import java.util.UUID;

public interface TaskDAO extends CrudRepository<Task> {

    void save(Task task);

    void saveList(ArrayList<Task> tasks);

    public void update(UUID id, Task task);

    public void delete(Task task);

    public Task read(int index);

    public ArrayList<Task> loadTasks();

}
