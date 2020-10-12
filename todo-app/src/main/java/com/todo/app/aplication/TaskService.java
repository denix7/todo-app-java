package com.todo.app.aplication;

import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.BusinessException;
import com.todo.app.filters.Filter;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public interface TaskService {

    void addTask(Task task);

    void modifyTask(UUID id, Task task);

    Task getTaskById(UUID id);

    ArrayList<Task> getAllTasks();

    ArrayList<Task> filterByTag(String tag);

    ArrayList<Task> filterByStatus(String status);

    ArrayList<Task> filterByPriority(String priority);

    public ArrayList<Task> filter(Filter filter);

    int countTasks(String element);

    ArrayList<String> getAllTags();

    Map<String, Integer> getAllTagsWithQuantity();

    boolean deleteTask(int index);

    String getInfo(int index);

    boolean exportAll();

    void config(String newPath);
}