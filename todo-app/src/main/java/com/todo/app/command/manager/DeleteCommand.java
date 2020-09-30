package com.todo.app.command.manager;

import com.todo.app.aplication.BusinessObject;
import com.todo.app.exceptions.BusinessException;
import com.todo.app.exceptions.CommandException;

import java.io.OutputStream;
import java.util.logging.Level;

public class DeleteCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "delete";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObject bo) throws CommandException {
        boolean result = false;
        if(args == null) {
            print(out, "Command not found");
        }
        else if(args.length == 1) {
            String indexExpected = args[0];
            boolean isNumeric = indexExpected.matches("-?\\d+(\\.\\d+)?");
            if(isNumeric) {
                int index = Integer.parseInt(args[0]) - 1;
                    try {
                        result = bo.deleteTask(index);
                    } catch (BusinessException exception) {
                        LOGGER.log(Level.SEVERE, "Delete Command: Error while storing", exception);
                        throw new CommandException("Delete Command Error", exception);
                    }
                print(out, result == true ? "Task deleted succesfull\n" : "The task doesn't exist\n");
            }
            else {
                print(out, "This index is not valid");
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
                print(out, "Task deleted");
            }
        }
        else {
            print(out, "Command not valid");
        }
    }

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}