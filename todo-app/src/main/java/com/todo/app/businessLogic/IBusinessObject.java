package com.todo.app.businessLogic;

import com.todo.app.entities.Task;

import java.util.ArrayList;

public interface IBusinessObject {

    void addTask(Task task);

    void modifyTask(Task task);

    void doneTask(Task task);

    ArrayList<Task> listTasks();

    ArrayList<Task> filterByTag(String tag);

    ArrayList<Task> filterByStatus(String status);

    ArrayList<Task> filterByPriority(String priority);

    int countTasks(String element);

    void getTags(String[] args);

    boolean deleteTask(int index);

    String getInfo(int index);

    void export(String[] args);

    void config(String[] args);
}