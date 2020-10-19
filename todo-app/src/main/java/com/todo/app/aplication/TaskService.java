package com.todo.app.aplication;

import com.todo.app.domain.entities.Task;
import com.todo.app.filters.Filter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TaskService {

    void addTask(Task task);

    void modifyTask(UUID id, Task task);

    Task getTaskById(UUID id);

    List<Task> getAllTasks();

    List<Task> find(Filter filter);

    int countTasks();

    int countTasksByFilter(Filter filter);

    List<String> getAllTags();

    Map<String, Integer> getAllTagsWithQuantity();

    boolean deleteTask(UUID id);

    boolean deleteByFilter(Filter filter);

    String getInfo(UUID id);

    boolean exportAll();

    void config(String newPath);
}