package com.todo.app.command.manager;

import com.todo.app.businessLogic.IBusinessObject;
import com.todo.app.entities.Task;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class DeleteCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "delete";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, IBusinessObject bo) {
        //bo.deleteTask(args);
        System.out.println(Arrays.toString(args));
        if(args == null) {
            System.out.println("Command not found");
        }
        else if(args.length == 1) {
            String indexExpected = args[0];
            boolean isNumeric = indexExpected.matches("-?\\d+(\\.\\d+)?");
            if(isNumeric) {
                int index = Integer.parseInt(args[0]) - 1;
                boolean result = bo.deleteTask(index);
                write(out, result == true ? "Task deleted succesfull\n" : "The task doesn't exist\n");
            }
            else {
                System.out.println("This index is not valid");
            }
        }
        else if(args.length == 2) {
            String filter = args[0];
            String value = args[1];

            if(filter.equals("tag:")) {
                //bo.deleteTaskByTag(value);
            }
            else if(filter.equals("priority:")) {
                if (value.equals("H") || value.equals("M") || value.equals("L")) {
                   // bo.deleteTaskByPriority(current);
                }
            }
            else if(filter.equals("status:")) {
                //bo.deleteTaskByStatus(current);
                write(out, "Task deleted");
            }
        }
        else {
            System.out.println("Command not valid");
        }
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
