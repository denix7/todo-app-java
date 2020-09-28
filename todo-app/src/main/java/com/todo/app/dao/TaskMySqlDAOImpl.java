package com.todo.app.dao;

import com.todo.app.entities.Task;
import com.todo.app.factory.FactoryDBAdapter;
import com.todo.app.factory.DBAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskMySqlDAOImpl implements TaskDAO {
    private DBAdapter adapter;

    public TaskMySqlDAOImpl(){
        adapter = FactoryDBAdapter.getAdapter();
    }

    @Override
    public void save(Task task) {
        Connection connection = adapter.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "insert into tasks(description, uuid, status, tag, priority, entry, due) values (?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);

            statement.setString(1, task.getDescription());
            statement.setString(2, task.getUuid().toString());
            statement.setString(3, task.getStatus());
            statement.setString(4, task.getTag());
            statement.setString(5, task.getPriority());
            statement.setString(6, task.getEntry());
            statement.setString(7, task.getDue());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger(TaskMySqlDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void saveList(ArrayList<Task> tasks) {
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
        Connection connection = adapter.getConnection();
        PreparedStatement statement = null;

        try{
            String sql = "update tasks set description = ?, tag = ?, status = ?, priority = ?, due = ? where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, task.getDescription());
            statement.setString(2, task.getTag());
            statement.setString(3, task.getStatus());
            statement.setString(4, task.getPriority());
            statement.setString(5, task.getDue());
            statement.setInt(6, task.getId());
            statement.executeUpdate();
        }
        catch(SQLException e){
            Logger.getLogger(TaskMySqlDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void delete(Task task) {
        Connection connection = adapter.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "delete from tasks where id = ?";

            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            Logger.getLogger(TaskMySqlDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Task read(Task task) {
        Connection connection = adapter.getConnection();
        PreparedStatement statement = null;
        ResultSet results = null;

        try{
            String sql = "select * from tasks where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getId());
            results = statement.executeQuery();

            Task current = new Task();
            while(results.next()) {
                int id = results.getInt(1);
                String description = results.getString(2);
                String uudi = results.getString(3);
                String status = results.getString(4);
                String tag = results.getString(5);
                String priority = results.getString(6);
                String entry = results.getString(7);

                current.setDescription(description);
                current.setId(id);
                current.setUuid(UUID.fromString(uudi));
                current.setStatus(status);
                current.setTag(tag);
                current.setPriority(priority);
                current.setEntry(entry);
            }
            return current;
        }
        catch(SQLException e){
            Logger.getLogger(TaskMySqlDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return null;
        } finally {
            if(results != null) {
                try {
                    results.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            if(statement != null){
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public ArrayList<Task> loadTasks() {
        Connection connection = adapter.getConnection();
        PreparedStatement statement = null;
        ResultSet results = null;

        try{
            String sql = "select * from tasks";
            statement = connection.prepareStatement(sql);
            results = statement.executeQuery();

            ArrayList<Task> tasks = new ArrayList<>();

            while(results.next()){
                int id = results.getInt(1);
                String description = results.getString(2);
                String uuid = results.getString(3);
                String status = results.getString(4);
                String tag = results.getString(5);
                String priority = results.getString(6);
                String entry = results.getString(7);
                String due = results.getString(8);

                Task current = new Task(description);
                current.setId(id);
                current.setUuid(UUID.fromString(uuid));
                current.setStatus(status);
                current.setTag(tag);
                current.setPriority(priority);
                current.setEntry(entry);
                current.setDue(due);

                tasks.add(current);
            }
            return tasks;
        }
        catch(SQLException e){
            e.printStackTrace();
            Logger.getLogger(TaskMySqlDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            return null;
        } finally {
            if(results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}