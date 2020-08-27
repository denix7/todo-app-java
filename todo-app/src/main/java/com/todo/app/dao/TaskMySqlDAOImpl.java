package com.todo.app.dao;

import com.todo.app.entities.Task;
import com.todo.app.factory.FactoryDBAdapter;
import com.todo.app.factory.IDBAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

public class TaskMySqlDAOImpl implements ITaskDAO {
    private IDBAdapter adapter;

    public TaskMySqlDAOImpl(){
        adapter = FactoryDBAdapter.getAdapter();
    }

    @Override
    public void save(Task task, String fileName, boolean exist) {
        try {
            String sql = "insert into tasks(description, uuid, status, tag, priority, entry) values (?,?,?,?,?,?)";
            Connection connection = adapter.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, task.getDescription());
            statement.setString(2, task.getUuid().toString());
            statement.setString(3, task.getStatus());
            statement.setString(4, task.getTag());
            statement.setString(5, task.getPriority());
            statement.setString(6, task.getEntry());
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveList(ArrayList<Task> tasks, String fileName, boolean exist) {
        try {
            for (Task task: tasks) {
                update(task);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Task task) {
        try{
            String sql = "update tasks set description = ?, tag = ?, status = ?, priority = ? where id = ?";
            Connection connection = adapter.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task.getDescription());
            statement.setString(2, task.getTag());
            statement.setString(3, task.getStatus());
            statement.setString(4, task.getPriority());
            statement.setInt(5, task.getId());
            statement.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Task task) {
        try {
            String sql = "delete from tasks where id = ?";
            Connection connection = adapter.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task.getUuid().toString());
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Task> loadTasks(String fileName) {
        try{
            String sql = "select * from tasks";
            Connection connection = adapter.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            ArrayList<Task> tasks = new ArrayList<Task>();

            while(results.next()){
                int id = results.getInt(1);
                String description = results.getString(2);
                String uudi = results.getString(3);
                String status = results.getString(4);
                String tag = results.getString(5);
                String priority = results.getString(6);
                String entry = results.getString(7);

                Task current = new Task(description);
                current.setId(id);
                current.setUuid(UUID.fromString(uudi));
                current.setStatus(status);
                current.setTag(tag);
                current.setPriority(priority);
                current.setEntry(entry);

                tasks.add(current);
            }
            return tasks;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}