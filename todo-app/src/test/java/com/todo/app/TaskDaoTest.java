package com.todo.app;

import com.todo.app.infrastructure.TaskDAO;
import com.todo.app.infrastructure.TaskMySqlDAOImpl;
import com.todo.app.domain.entities.Task;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TaskDaoTest {
    private TaskDAO taskDAO = new TaskMySqlDAOImpl();

    @Test
    public void get() throws SQLException, ClassNotFoundException {
        UUID uuid = UUID.randomUUID();
        Task taskExpected = new Task();

        taskExpected.setUuid(uuid);

        Task task = null;
        try {
            task = taskDAO.read(uuid);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        assertEquals (uuid, task.getUuid());
    }

    @Test
    public void add() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        UUID uuid = UUID.randomUUID();
        String description = "new task";
        String priority = "H";
        String tag = "market";
        String status = "completed";

        Task task = new Task();

        task.setDescription(description);
        task.setUuid(uuid);
        task.setStatus(status);
        task.setTag(tag);
        task.setPriority(priority);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        task.setEntry(dtf.format(now));
        task.setDue(dtf.format(now));

        try {
            taskDAO.save(task);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Task taskSaved = null;
        try {
            taskSaved = taskDAO.read(uuid);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        assertEquals(taskSaved.getUuid(), uuid);
    }

    @Test
    public void delete() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        UUID uuid = UUID.randomUUID();
        String description = "new task";
        String priority = "H";
        String tag = "market";
        String status = "completed";

        Task task = new Task();

        task.setDescription(priority);
        task.setUuid(uuid);
        task.setStatus(status);
        task.setTag(tag);
        task.setPriority(priority);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        task.setEntry(dtf.format(now));

        try {
            taskDAO.delete(uuid);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        Task taskReaded = null;
        try {
            taskReaded = taskDAO.read(uuid);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        assertNull(taskReaded);
    }
}
