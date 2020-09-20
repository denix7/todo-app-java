package com.todo.app.command.manager;

import com.todo.app.businessLogic.BusinessObjectTxtImpl;
import com.todo.app.businessLogic.IBusinessObject;
import com.todo.app.entities.Task;

import java.io.OutputStream;
import java.util.Arrays;

public class DoneCommand extends AbstractCommand {

    public static final String COMMAND_NAME = "done";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, IBusinessObject bo) {
        String indexExpected = args[0];
        boolean isNumeric = indexExpected.matches("-?\\d+(\\.\\d+)?");
        Task task = new Task();

        if (isNumeric && args.length == 1) {
            int index = Integer.parseInt(indexExpected);
            task.setId(index - 1);
            task.setStatus("completed");

            bo.doneTask(task);
            write(out, "Marked as done\n");
        }
        else if(!isNumeric && args.length == 2){
            String tag = args[1];
            if(args[0].equals("tag:")){
                task.setTag(tag);
                task.setStatus("completed");
                bo.doneTask(task);
                write(out, "All tasks with tag marked as done\n");
            }
            else{
                write(out, "Command not valid\n");
            }
        }
        else{
            write(out, "Command not valid\n");
        }
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
