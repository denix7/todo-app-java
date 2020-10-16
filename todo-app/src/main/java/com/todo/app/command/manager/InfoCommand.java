package com.todo.app.command.manager;

import com.todo.app.aplication.TaskService;
import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.BusinessException;
import com.todo.app.exceptions.CommandException;

import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;

public class InfoCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "info";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, TaskService bo) {
        if (args == null) {
            print(out, "Command not valid\n");
        } else if (args.length == 1) {
            String indexExpected = args[0];
            boolean isNumeric = indexExpected.matches("-?\\d+(\\.\\d+)?");

            if (isNumeric) {
                int index = Integer.parseInt(indexExpected) - 1;
                String taskInfo = "";

                List<Task> tasks = bo.getAllTasks();
                Task current = tasks.get(index);

                taskInfo = bo.getInfo(current.getUuid());
                if (taskInfo != null) {
                    print(out, taskInfo);
                } else {
                    print(out, "Task not found\n");
                }
            } else {
                print(out, "The param is incorrect\n");
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
