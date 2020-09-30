package com.todo.app.aplication;

import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.BusinessException;
import com.todo.app.filters.Filter;

import java.util.ArrayList;
import java.util.Map;

public interface BusinessObject {

    void addTask(Task task) throws BusinessException;

    void modifyTask(Task task) throws BusinessException;

    void doneTask(Task task) throws BusinessException;

    ArrayList<Task> listTasks();

    ArrayList<Task> filterByTag(String tag);

    ArrayList<Task> filterByStatus(String status);

    public ArrayList<Task> filter(Filter filter);

    ArrayList<Task> filterByPriority(String priority);

    int countTasks(String element);

    ArrayList<String> getAllTags();

    Map<String, Integer> getAllTagsWithQuantity();

    boolean deleteTask(int index) throws BusinessException;

    String getInfo(int index);

    boolean exportAll();

    void config(String[] args);
}