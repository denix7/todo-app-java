package com.todo.app.dao;

import com.todo.app.entities.Task;

import java.util.ArrayList;

public interface ITaskDAO {

    void save(Task task);

    void saveList(ArrayList<Task> tasks);

    public void update(Task task);

    public void delete(Task task);

    public Task read(int key);

    public ArrayList<Task> loadTasks();

}
