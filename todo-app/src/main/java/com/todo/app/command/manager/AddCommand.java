package com.todo.app.command.manager;

import com.todo.app.aplication.TaskService;
import com.todo.app.dependencyInjection.Injector;
import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.BusinessException;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.logging.Level;

public class AddCommand extends AbstractCommand{

    public static final String COMMAND_NAME = "add";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, TaskService bo) {
        if(args != null && args.length == 1) {
            print(out, "Adding element with title\n");
            String description = args[0];
            Task task = Injector.getTask();
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

            try {
                bo.addTask(task);
            } catch (BusinessException exception) {
                LOGGER.log(Level.SEVERE, "Add Command: Error while storing", exception);
            }
        }
        if(args == null || args.length == 0) {
            print(out, "You should add a note");
        }
        if(args != null && args.length == 2 ) {
            if(args[1].equals("M") || args[1].equals("H") || args[1].equals("L")){
                String description = args[0];
                String priority = args[1];
                Task task = Injector.getTask();
                task.setDescription(description);
                task.setPriority(priority);
                UUID id = UUID.randomUUID();
                task.setUuid(id);
                task.setStatus("pending");
                task.setTag("default");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                task.setEntry(dtf.format(now));
                task.setDue(dtf.format(now));

                try {
                    bo.addTask(task);
                } catch (BusinessException exception) {
                    LOGGER.log(Level.SEVERE, "Add Command: Error while storing", exception);
                }
                print(out, "Task with priority added\n");
            }
            else {
                print(out,"Task not added, priority only could be: L/M/H");
            }
        }
    }

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}
