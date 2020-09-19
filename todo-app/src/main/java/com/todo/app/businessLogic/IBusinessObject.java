package com.todo.app.businessLogic;

import com.todo.app.entities.Task;

public interface IBusinessObject {

    void addTask(Task task);

    void modifyTask(Task task);

    void doneTask(String[] args);

    void listTasks(String[] args);

    int countTasks(String element);

    void getTags(String[] args);

    boolean deleteTask(int index);

    String getInfo(int index);

    void export(String[] args);

    void config(String[] args);
}