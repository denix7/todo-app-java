package com.todo.app.command.manager;

import com.todo.app.aplication.TaskService;
import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.BusinessException;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class DoneCommand extends AbstractCommand {

    public static final String COMMAND_NAME = "done";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, TaskService bo) {
        String indexExpected = args[0];
        boolean isNumeric = indexExpected.matches("-?\\d+(\\.\\d+)?");

        if (isNumeric && args.length == 1) {
            int index = Integer.parseInt(indexExpected);

            List<Task> tasks = bo.getAllTasks();
            Task task = tasks.get(index - 1);
            task.setStatus("completed");

            try {
                bo.modifyTask(task.getUuid(), task);
            } catch (BusinessException exception) {
                LOGGER.log(Level.SEVERE, "Done Command: Error while storing", exception);
            }
            print(out, "Marked as done\n");
        }
        else if(!isNumeric && args.length == 2){
            System.out.println(Arrays.toString(args));
            String tag = args[1];
            if(args[0].equals("tag:")){
                List<Task> tasks = bo.getAllTasks();

                for (Task current: tasks) {
                    if(current.getTag().equals(tag)) {
                        current.setStatus("completed");
                        try {
                            bo.modifyTask(current.getUuid(), current);
                        } catch (BusinessException exception) {
                            LOGGER.log(Level.SEVERE, "Done Command: Error while storing", exception);
                        }
                    }
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
