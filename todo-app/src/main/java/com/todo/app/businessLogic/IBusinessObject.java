package com.todo.app.businessLogic;

public interface IBusinessObject {

    void addTask(String[] args);

    void modifyTask(String[] args);

    void doneTask(String[] args);

    void listTasks(String[] args);

    void countTasks(String[] args);

    void getTags(String[] args);

    void deleteTask(String[] args);

    void getInfo(String[] args);

    void export(String[] args);

    void config(String[] args);
}