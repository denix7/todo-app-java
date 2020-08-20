package com.todo.app.businessLogic;

import com.todo.app.entities.Task;
import java.util.ArrayList;

public interface BusinessObject {

    void addTask(String[] args, String fileName);

    void modifyTask(String[] args, String fileName);

    void modifyTaskByIndex(ArrayList<Task> tasks, String fileName, String[] args);

    void doneTask(String[] args, String fileName);

    void listTasks(String[] args, String fileName);

    ArrayList<Task> filterByTag(ArrayList<Task> tasks, String tag);

    void loadFile(String fileName);
}
