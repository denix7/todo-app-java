package com.todo.app.command.manager;

import com.todo.app.aplication.BusinessObject;
import com.todo.app.domain.entities.Task;

import java.io.OutputStream;

public class DoneCommand extends AbstractCommand {

    public static final String COMMAND_NAME = "done";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObject bo) {
        String indexExpected = args[0];
        boolean isNumeric = indexExpected.matches("-?\\d+(\\.\\d+)?");
        Task task = new Task();

        if (isNumeric && args.length == 1) {
            int index = Integer.parseInt(indexExpected);
            task.setId(index - 1);
            task.setStatus("completed");

            try {
                bo.doneTask(task);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            write(out, "Marked as done\n");
        }
        else if(!isNumeric && args.length == 2){
            String tag = args[1];
            if(args[0].equals("tag:")){
                task.setTag(tag);
                task.setStatus("completed");

                try {
                    bo.doneTask(task);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
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
