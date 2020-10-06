package com.todo.app.command.manager;

import com.todo.app.aplication.BusinessObject;
import com.todo.app.exceptions.CommandException;

import java.io.OutputStream;
import java.util.logging.Level;

public class CountCommand extends AbstractCommand {

    public static final String COMMAND_NAME = "count";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObject bo) {
        int result = 0;

        if(args == null){
            try {
                result = bo.countTasks("");
            } catch (Exception exception) {
                LOGGER.log(Level.SEVERE, "Count Command: Error while storing", exception);
            }
            print(out, "There are : " + result + " tasks founded\n");
        }
        else if(args.length == 2) {
            String filter = args[0];
            String value = args[1];

            if(filter.equals("priority:")){
                if(value.equals("H") || value.equals("M") || value.equals("L")) {
                    try {
                        result = bo.countTasks(value);
                    } catch (Exception exception) {
                        LOGGER.log(Level.SEVERE, "Count Command: Error while storing", exception);
                    }
                }
            }
            else if(filter.equals("status:")) {
                try {
                    result = bo.countTasks(value);
                } catch (Exception exception) {
                    LOGGER.log(Level.SEVERE, "Count Command: Error while storing", exception);
                }
            }
            else if(filter.equals("tag:")) {
                try {
                    result = bo.countTasks(value);
                } catch (Exception exception) {
                    LOGGER.log(Level.SEVERE, "Count Command: Error while storing", exception);
                }
            }
            else {
                print(out, "Params not valid\n");
            }

            print(out, "There are : " + result + " tasks founded\n");
        }
        else {
            print(out, "Command not found\n");
        }
    }

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}
