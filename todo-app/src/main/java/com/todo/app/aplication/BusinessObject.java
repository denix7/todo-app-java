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

    ArrayList<Task> listTasks() throws BusinessException;

    ArrayList<Task> filterByTag(String tag) throws BusinessException;

    ArrayList<Task> filterByStatus(String status) throws BusinessException;

    public ArrayList<Task> filter(Filter filter) throws BusinessException;

    ArrayList<Task> filterByPriority(String priority) throws BusinessException;

    int countTasks(String element) throws BusinessException;

    ArrayList<String> getAllTags() throws BusinessException;

    Map<String, Integer> getAllTagsWithQuantity() throws BusinessException;

    boolean deleteTask(int index) throws BusinessException;

    String getInfo(int index) throws BusinessException;

    boolean exportAll() throws BusinessException;

    void config(String[] args);
}