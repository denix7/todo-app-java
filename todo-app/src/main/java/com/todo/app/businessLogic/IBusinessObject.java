package com.todo.app.businessLogic;

public interface IBusinessObject {

    void addTask(String name, String priority);

    void modifyTask(int index, String description, String tag, String priority);

    void doneTask(String[] args);

    void listTasks(String[] args);

    int countTasks(String element);

    void getTags(String[] args);

    boolean deleteTask(int index);

    String getInfo(int index);

    void export(String[] args);

    void config(String[] args);
}