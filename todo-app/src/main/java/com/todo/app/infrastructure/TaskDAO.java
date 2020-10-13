package com.todo.app.infrastructure;

import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.PersistentException;
import com.todo.app.filters.Filter;

import java.util.List;
import java.util.UUID;

public interface TaskDAO extends CrudRepository<Task> {

    void save(Task task);

    void saveList(List<Task> tasks);

    public void update(UUID id, Task task);

    public void delete(Task task);

    public Task read(int index);

    public List<Task> loadTasks();

    public List<Task> find(Filter filter);

    public int count();

    public int countByFilter(Filter filter);
}
