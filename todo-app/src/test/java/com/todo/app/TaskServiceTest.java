package com.todo.app;

import com.todo.app.aplication.TaskService;
import com.todo.app.aplication.TaskServiceImp;
import com.todo.app.command.manager.Command;
import com.todo.app.command.manager.CommandManager;
import com.todo.app.dependencyInjection.Injector;
import com.todo.app.domain.entities.Task;
import com.todo.app.infrastructure.TaskMySqlDAOImpl;
import com.todo.app.util.Fragmenter;
import org.junit.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TaskServiceTest {
    TaskService taskService = Injector.getTaskService();

    @Test
    public void itShouldSaveTask() throws SQLException, ClassNotFoundException {
        Task task = Injector.getTask();
        UUID uuid = UUID.randomUUID();

        task.setDescription("new task test");
        task.setStatus("Pending");
        task.setPriority("H");
        task.setUuid(uuid);

        //TaskServiceImp taskService = Injector.getTaskService();
        taskService.addTask(task);

        Task taskExpected = taskService.getTaskById(uuid);
        assertEquals(taskExpected.getUuid(), uuid);
    }

    public void itShouldGetById() throws SQLException, ClassNotFoundException {

    }
}
