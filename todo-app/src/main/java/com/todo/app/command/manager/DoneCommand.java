package com.todo.app.command.manager;

import com.todo.app.aplication.BusinessObject;
import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.BusinessException;
import com.todo.app.exceptions.CommandException;

import java.io.OutputStream;
import java.util.logging.Level;

public class DoneCommand extends AbstractCommand {

    public static final String COMMAND_NAME = "done";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObject bo) throws CommandException {
        String indexExpected = args[0];
        boolean isNumeric = indexExpected.matches("-?\\d+(\\.\\d+)?");
        Task task = new Task();

        if (isNumeric && args.length == 1) {
            int index = Integer.parseInt(indexExpected);
            task.setId(index - 1);
            task.setStatus("completed");

            try {
                bo.doneTask(task);
            } catch (BusinessException exception) {
                LOGGER.log(Level.SEVERE, "Done Command: Error while storing", exception);
                throw new CommandException("Done Command Error", exception);
            }
            print(out, "Marked as done\n");
        }
        else if(!isNumeric && args.length == 2){
            String tag = args[1];
            if(args[0].equals("tag:")){
                task.setTag(tag);
                task.setStatus("completed");

                try {
                    bo.doneTask(task);
                } catch (Exception exception) {
                    LOGGER.log(Level.SEVERE, "Done Command: Error while storing", exception);
                    throw new CommandException("Done Command Error", exception);
                }
                print(out, "All tasks with tag marked as done\n");
            }
            else{
                print(out, "Command not valid\n");
            }
        }
        else{
            print(out, "Command not valid\n");
        }
    }

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}
