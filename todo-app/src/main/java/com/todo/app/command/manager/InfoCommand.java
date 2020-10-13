package com.todo.app.command.manager;

import com.todo.app.aplication.TaskService;
import com.todo.app.exceptions.BusinessException;
import com.todo.app.exceptions.CommandException;

import java.io.OutputStream;
import java.util.logging.Level;

public class InfoCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "info";
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, TaskService bo) {
        if(args == null) {
            print(out, "Command not valid\n");
        }
        else if(args.length == 1){
            String indexExpected = args[0];
            boolean isNumeric = indexExpected.matches("-?\\d+(\\.\\d+)?");

            if (isNumeric) {
                int index = Integer.parseInt(indexExpected);
                String taskInfo = "";

                try {
                    taskInfo = bo.getInfo(index);
                    if(taskInfo != null) {
                        print(out, taskInfo);
                    } else {
                        print(out, "Task not found\n");
                    }
                } catch (BusinessException exception) {
                    LOGGER.log(Level.SEVERE, "Info Command: Error while reading", exception);
                    throw new CommandException("Error. Unable execute the info command", exception);
                }
            }
            else{
                print(out, "The param is incorrect\n");
            }
        }
        else {
            print(out, "Command not valid\n");
        }
    }

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}
