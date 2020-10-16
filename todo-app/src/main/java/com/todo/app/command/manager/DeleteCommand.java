package com.todo.app.command.manager;

import com.todo.app.aplication.TaskService;
import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.BusinessException;
import com.todo.app.exceptions.CommandException;
import com.todo.app.filters.Filter;
import com.todo.app.filters.PriorityFilter;
import com.todo.app.filters.StatusFilter;
import com.todo.app.filters.TagFilter;

import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;

public class DeleteCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "delete";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, TaskService bo) {
        boolean result;
        if (args == null) {
            print(out, "Command not found\n");
        } else if (args.length == 1) {
            String indexExpected = args[0];
            boolean isNumeric = indexExpected.matches("-?\\d+(\\.\\d+)?");
            if (isNumeric) {
                int index = Integer.parseInt(args[0]) - 1;
                List<Task> tasks = bo.getAllTasks();
                Task current = tasks.get(index);

                result = bo.deleteTask(current.getUuid());
                print(out, result == false ? "Task deleted succesfull\n" : "The task doesn't exist\n");
            } else {
                print(out, "This index is not valid\n");
            }
        } else if (args.length == 2) {
            if (args[0].equals("tag:")) {
                String tag = args[1];
                Filter filter = new TagFilter(tag);
                try {
                    bo.deleteByFilter(filter);
                    print(out, "Deleted all tasks with tag : " + tag + "\n");
                } catch (Exception exception) {
                    LOGGER.log(Level.SEVERE, "List Command: Error while reading", exception);
                    throw new CommandException("Error. Unable execute the list command", exception);
                }
            } else if (args[0].equals("status:")) {
                String status = args[1];
                Filter filter = new StatusFilter(status);
                try {
                    bo.deleteByFilter(filter);
                    print(out, "Deleted all tasks with status : " + status + "\n");
                } catch (BusinessException exception) {
                    LOGGER.log(Level.SEVERE, "List Command: Error while reading", exception);
                    throw new CommandException("Error. Unable execute the list command", exception);
                }
            } else if (args[0].equals("priority:")) {
                String priority = args[1];
                Filter filter = new PriorityFilter(priority);
                try {
                    bo.deleteByFilter(filter);
                    print(out, "Deleted all tasks with priority : " + priority + "\n");
                } catch (Exception exception) {
                    LOGGER.log(Level.SEVERE, "List Command: Error while reading", exception);
                    throw new CommandException("Error. Unable execute the list command", exception);
                }
            } else {
                print(out, "Filter no valid\n");
            }
        } else {
            print(out, "Command not valid\n");
        }
    }

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}