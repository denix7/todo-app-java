package com.todo.app.infrastructure;

import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.PersistentException;
import com.todo.app.filters.Filter;

import java.util.List;
import java.util.UUID;

public interface TaskDAO extends CrudRepository<Task> {

    void save(Task task);

    void saveList(List<Task> tasks);

    void update(UUID id, Task task);

    boolean delete(UUID id);

    boolean deleteByFilter(Filter filter);

    Task read(UUID id);

    List<Task> loadTasks();

    List<Task> find(Filter filter);

    int count();

    int countByFilter(Filter filter);
}
