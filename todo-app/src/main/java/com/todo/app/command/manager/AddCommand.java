package com.todo.app.command.manager;

import com.todo.app.aplication.TaskService;
import com.todo.app.dependencyInjection.Injector;
import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.BusinessException;
import com.todo.app.exceptions.CommandException;

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
        Task task = Injector.getTask();

        if(args != null) {
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

            if(args.length == 1){
                try {
                    bo.addTask(task);
                    System.out.println("Task added successfully");
                } catch (BusinessException exception) {
                    LOGGER.log(Level.SEVERE, "Add Command: Error while storing", exception);
                    throw new CommandException("Error. Unable execute the add command", exception);
                }
            }
            if(args.length == 2 ) {
                if(args[1].equals("M") || args[1].equals("H") || args[1].equals("L")){
                    String priority = args[1];
                    task.setPriority(priority);

                    try {
                        bo.addTask(task);
                        System.out.println("Task added successfully");
                    } catch (BusinessException exception) {
                        LOGGER.log(Level.SEVERE, "Add Command: Error while storing", exception);
                        throw new CommandException("Error. Unable execute the add command", exception);
                    }
                }
                else {
                    print(out,"Task not added, priority only could be: L/M/H");
                }
            }
        }
    }

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}
