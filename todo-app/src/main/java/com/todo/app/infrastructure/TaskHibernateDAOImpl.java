package com.todo.app.infrastructure;

import com.todo.app.domain.entities.Task;
import com.todo.app.filters.Filter;

import java.util.List;
import java.util.UUID;

public class TaskHibernateImpl implements TaskDAO {

    @Override
    public void save(Task task) {

    }

    @Override
    public void saveList(List<Task> tasks) {

    }

    @Override
    public void update(UUID id, Task task) {

    }

    @Override
    public void delete(Task task) {

    }

    @Override
    public Task read(int index) {
        return null;
    }

    @Override
    public List<Task> loadTasks() {
        return null;
    }

    @Override
    public List<Task> find(Filter filter) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int countByFilter(Filter filter) {
        return 0;
    }
}
