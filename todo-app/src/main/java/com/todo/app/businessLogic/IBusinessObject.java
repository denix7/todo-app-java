package com.todo.app.businessLogic;

import com.todo.app.entities.Task;

import java.util.ArrayList;
import java.util.Map;

public interface IBusinessObject {

    void addTask(Task task);

    void modifyTask(Task task);

    void doneTask(Task task);

    ArrayList<Task> listTasks();

    ArrayList<Task> filterByTag(String tag);

    ArrayList<Task> filterByStatus(String status);

    ArrayList<Task> filterByPriority(String priority);

    int countTasks(String element);

    ArrayList<String> getAllTags();

    Map<String, Integer> getAllTagsWithQuantity();

    boolean deleteTask(int index);

    String getInfo(int index);

    void export(String[] args);

    void config(String[] args);
}