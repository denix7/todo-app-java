package com.todo.app.dao;

import com.todo.app.entities.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public interface ITaskDAO {

    void save(Task task, boolean exist);

    void saveList(ArrayList<Task> tasks, boolean exist);

    public void update(Task task);

    public void delete(Task task);

    public ArrayList<Task> loadTasks();

}
