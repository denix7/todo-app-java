package com.todo.app.command.manager;

import com.todo.app.aplication.TaskService;
import com.todo.app.dependencyInjection.Injector;
import com.todo.app.domain.entities.Task;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class AddCommand extends AbstractCommand{

    public static final String COMMAND_NAME = "add";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, TaskService bo) {
        Task task = Injector.getTask();

        if (args != null) {
            String description = args[0];
            task.setDescription(description);
            UUID id = UUID.randomUUID();
            task.setUuid(id);
            task.setStatus("pending");
            task.setTag("default");
            task.setPriority("M");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            task.setEntry(dtf.format(now));
            task.setDue(dtf.format(now));

            if (args.length == 1) {
                bo.addTask(task);
                print(out, "Task added successfully\n");
            }
            if (args.length == 2) {
                if (args[1].equals("M") || args[1].equals("H") || args[1].equals("L")) {
                    String priority = args[1];
                    task.setPriority(priority);

                    bo.addTask(task);
                    print(out, "Task added successfully\n");
                } else {
                    print(out, "Task not added, priority only could be: L/M/H\n");
                }
            }
        }
    }

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}
