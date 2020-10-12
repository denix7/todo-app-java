package com.todo.app.infrastructure;

import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.PersistentException;
import com.todo.app.factory.FactoryDBAdapter;
import com.todo.app.factory.DBAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskMySqlDAOImpl implements TaskDAO {
    private DBAdapter adapter;
    private static final Logger LOGGER = Logger.getLogger(TaskMySqlDAOImpl.class.getName());

    public TaskMySqlDAOImpl(){
        adapter = FactoryDBAdapter.getAdapter();
    }

    @Override
    public void save(Task task) {
        String sql = "insert into tasks(description, uuid, status, tag, priority, entry, due) values (?,?,?,?,?,?,?)";

        try (Connection connection = adapter.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, task.getDescription());
            statement.setString(2, task.getUuid().toString());
            statement.setString(3, task.getStatus());
            statement.setString(4, task.getTag());
            statement.setString(5, task.getPriority());
            statement.setString(6, task.getEntry());
            statement.setString(7, task.getDue());
            statement.executeUpdate();

        }
        catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Unable to save task", exception);
            throw new PersistentException("Error while saving in DB", exception);
        }
    }

    @Override
    public void saveList(ArrayList<Task> tasks) {
        try {
            for (Task task: tasks) {
                update(task);
            }
        }
        catch(Exception exception) {
            LOGGER.log(Level.SEVERE, "Unable to update task", exception);
        }
    }

    @Override
    public void update(Task task) {
        String sql = "update tasks set description = ?, tag = ?, status = ?, priority = ?, due = ? where id = ?";

        try (Connection connection = adapter.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);){

            statement.setString(1, task.getDescription());
            statement.setString(2, task.getTag());
            statement.setString(3, task.getStatus());
            statement.setString(4, task.getPriority());
            statement.setString(5, task.getDue());
            statement.setInt(6, task.getId());
            statement.executeUpdate();
        }
        catch(SQLException exception){
            LOGGER.log(Level.SEVERE, "Unable to update task", exception);
            throw new PersistentException("Error while storing in DB", exception);
        }
    }

    @Override
    public void delete(Task task) {
        String sql = "delete from tasks where id = ?";

        try (Connection connection = adapter.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setInt(1, task.getId());
            statement.executeUpdate();
        }
        catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Unable to delete task", exception);
            throw new PersistentException("Error while deleting in DB", exception);
        }
    }

    @Override
    public Task read(int index) {
        String sql = "select * from tasks where id = ?";
        Task current = null;

        try (Connection connection = adapter.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);){

            statement.setInt(1, index);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                int id = results.getInt(1);
                String description = results.getString(2);
                String uudi = results.getString(3);
                String status = results.getString(4);
                String tag = results.getString(5);
                String priority = results.getString(6);
                String due = results.getString(7);
                String entry = results.getString(8);
                Date start = results.getDate(9);
                Date end = results.getDate(10);

                current = new Task();
                current.setDescription(description);
                current.setId(id);
                current.setUuid(UUID.fromString(uudi));
                current.setStatus(status);
                current.setTag(tag);
                current.setPriority(priority);
                current.setDue(due);
                current.setEntry(entry);
                current.setStart(start);
                current.setEnd(end);
            }
        }
        catch(SQLException exception){
            LOGGER.log(Level.SEVERE, "Unable to read task " + index, exception);
            throw new PersistentException("Error while reading in DB", exception);
        } finally {
            return current;
        }
    }

    @Override
    public ArrayList<Task> loadTasks() {
        String sql = "select * from tasks";
        ArrayList<Task> tasks = new ArrayList<>();

        try (Connection connection = adapter.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()){

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
        }
        catch(SQLException exception){
            LOGGER.log(Level.SEVERE, "Unable to load Tasks", exception);
            throw new PersistentException("Error while reading in DB", exception);
        } finally {
            return tasks;
        }
    }
}