package com.todo.app.dao;

import com.todo.app.entities.Task;

import java.util.ArrayList;

public interface TaskDAO {

    void save(Task task);

    void saveList(ArrayList<Task> tasks);

    public void update(Task task);

    public void delete(Task task);

    public Task read(Task task);

    public ArrayList<Task> loadTasks();

}
