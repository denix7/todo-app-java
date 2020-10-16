package com.todo.app.infrastructure;

import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.PersistentException;
import com.todo.app.factory.FactoryDBAdapter;
import com.todo.app.factory.DBAdapter;
import com.todo.app.filters.Filter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public void saveList(List<Task> tasks) {
        try {
            for (Task task: tasks) {
                update(task.getUuid(), task);
            }
        }
        catch(Exception exception) {
            LOGGER.log(Level.SEVERE, "Unable to update task", exception);
        }
    }

    @Override
    public void update(UUID id, Task task) {
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
    public boolean delete(UUID uuid) {
        String sql = "delete from tasks where uuid = ?";

        try (Connection connection = adapter.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, uuid.toString());
            statement.executeUpdate();

            return (statement.executeUpdate() > 0);
        }
        catch (SQLException | NumberFormatException exception ) {
            LOGGER.log(Level.SEVERE, "Unable to delete task", exception);
            throw new PersistentException("Error while deleting in DB", exception);
        }
    }

    public boolean deleteByFilter(Filter filter) {
        String sql = "delete from tasks where " + filter.getName() + "=?";

        try (Connection connection = adapter.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, filter.getValue());
            statement.executeUpdate();

            return statement.executeUpdate() > 0;

        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Unable to find task", exception);
            throw new PersistentException("Error while finding in DB", exception);
        }
    }

    @Override
    public Task read(UUID uuid) {
        String sql = "select * from tasks where uuid = ?";
        Task current = null;

        try (Connection connection = adapter.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);){

            statement.setString(1, uuid.toString());
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
            LOGGER.log(Level.SEVERE, "Unable to read task " + uuid, exception);
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

    @Override
    public ArrayList<Task> find(Filter filter) {
        String sql = "select * from tasks where " + filter.getName() + "=?";
        ArrayList<Task> tasks = new ArrayList<>();

        try (Connection connection = adapter.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, filter.getValue());
            ResultSet results = statement.executeQuery();

            while (results.next()) {
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

        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Unable to find task", exception);
            throw new PersistentException("Error while finding in DB", exception);
        } finally {
            return tasks;
        }
    }

    @Override
    public int count() {
        String sql = "select count(*) as total from tasks";
        ArrayList<Task> tasks = new ArrayList<>();
        int counter = 0;

        try (Connection connection = adapter.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()){

            while (results.next()) {
                counter = results.getInt("total");
            }

        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Unable to find task", exception);
            throw new PersistentException("Error while finding in DB", exception);
        } finally {
            return counter;
        }
    }

    @Override
    public int countByFilter(Filter filter) {
        String sql = "select count(*) as total from tasks where " + filter.getName() + "=?";
        ArrayList<Task> tasks = new ArrayList<>();
        int counter = 0;

        try (Connection connection = adapter.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, filter.getValue());
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                counter = results.getInt("total");
            }

            return counter;

        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Unable to find task", exception);
            throw new PersistentException("Error while finding in DB", exception);
        }
    }
}