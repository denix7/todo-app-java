package com.todo.app.dao;

import com.todo.app.entities.Task;
import com.todo.app.factory.FactoryDAO;
import com.todo.app.factory.IDBAdapter;
import com.todo.app.factory.MySQLAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class TaskMySqlDAOImpl implements ITaskDAO {
    private IDBAdapter adapter;

    public TaskMySqlDAOImpl(){
        adapter = FactoryDAO.getAdapter();
    }

    @Override
    public void save(Task task, String fileName, boolean exist) {
        try {
            String sql = "insert into tasks(description) values (?)";
            Connection connection = adapter.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task.getDescription());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveList(ArrayList<Task> tasks, String fileName, boolean exist) {

    }

    @Override
    public void update(Task task) {
        try{
            String sql = "UPDATE tasks set description where id = ?";
            Connection connection = adapter.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task.getUuid().toString());
            statement.executeUpdate();
        } catch(Exception e){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Task> loadTasks(String fileName) {
        return null;
    }
}
