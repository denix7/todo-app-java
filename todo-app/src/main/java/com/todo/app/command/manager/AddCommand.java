package com.todo.app.command.manager;

import com.todo.app.businessLogic.IBusinessObject;
import com.todo.app.entities.Task;

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
    public void execute(String[] args, OutputStream out, IBusinessObject bo) {
        if(args != null && args.length == 1) {
            write(out, "Adding element with title\n");
            String description = args[0];
            Task task = new Task(description);
            UUID id = UUID.randomUUID();
            task.setUuid(id);
            task.setStatus("pending");
            task.setTag("default");
            task.setPriority("M");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            task.setEntry(dtf.format(now));

            task.setDue(dtf.format(now));
            bo.addTask(task);
        }
        if(args == null || args.length == 0) {
            write(out, "You should add a note");
        }
        if(args != null && args.length == 2 ) {
            if(args[1].equals("M") || args[1].equals("H") || args[1].equals("L")){
                String description = args[0];
                String priority = args[1];
                Task task = new Task(description, priority);
                UUID id = UUID.randomUUID();
                task.setUuid(id);
                task.setStatus("pending");
                task.setTag("default");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                task.setEntry(dtf.format(now));

                bo.addTask(task);
                write(out, "Task with priority added\n");
            }
            else {
                write(out,"Task not added, priority only could be: L/M/H");
            }
        }
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
