package com.todo.app.businessLogic;

public interface IBusinessObject {

    void addTask(String[] args, String fileName);

    void modifyTask(String[] args, String fileName);

    void doneTask(String[] args, String fileName);

    void listTasks(String[] args, String fileName);

    void countTasks(String[] args, String fileName);

    void getTags(String[] args, String fileName);

    void deleteTask(String[] args, String fileName);

    void getInfo(String[] args, String fileName);

    void export(String[] args, String fileName);

    void config(String[] args, String fileName);
}