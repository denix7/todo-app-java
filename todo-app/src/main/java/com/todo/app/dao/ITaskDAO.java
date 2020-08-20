package com.todo.app.dao;

import com.todo.app.entities.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public interface ITaskDAO {
    boolean exist(String fileName);

    void save(Task task, String fileName, boolean exist);

    void saveList(ArrayList<Task> tasks, String fileName, boolean exist);

    public boolean update();

    public ArrayList<Task> loadTasks(String fileName);

    public void createFile(String fileName);

    public void deleteFile(String fileName);
}
