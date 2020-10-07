package com.todo.app.command.manager;

import com.todo.app.aplication.BusinessObject;
import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.BusinessException;
import com.todo.app.exceptions.CommandException;

import java.io.OutputStream;
import java.util.logging.Level;

public class ModifyCommand extends AbstractCommand {

    public static final String COMMAND_NAME = "modify";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObject bo) {
        boolean numeric;
        numeric = args[0].matches("-?\\d+(\\.\\d+)?");
        if(args == null || args.length == 1 || args.length > 5) {
            print(out, "Command not valid\n");
        }
        else if (numeric && args.length == 2) {
            int taskIndex = Integer.parseInt(args[0]);
            String newDescription = args[1];

            Task task = new Task();
            task.setId(taskIndex-1);
            task.setDescription(newDescription);

            try {
                bo.modifyTask(task);
            } catch (BusinessException exception) {
                LOGGER.log(Level.SEVERE,"Modify Command: Error while modifying", exception);
            }
        }
        else if(numeric && args.length == 3 && args[1].equals("tag:")) {
            int taskIndex = Integer.parseInt(args[0]);
            String newTag = args[2];

            Task task = new Task();
            task.setId(taskIndex-1);
            task.setTag(newTag);

            try {
                bo.modifyTask(task);
            } catch (BusinessException exception) {
                LOGGER.log(Level.SEVERE,"Modify Command: Error while modifying", exception);
            }
        }
        else if (numeric && args.length == 3 && args[1].equals("priority:")) {
            String newPriority = args[2];
            int taskIndex = Integer.parseInt(args[0]);
            if (newPriority.equals("H") || newPriority.equals("M") || newPriority.equals("L")){
                Task task = new Task();
                task.setId(taskIndex - 1);
                task.setPriority(newPriority);

                try {
                    bo.modifyTask(task);
                } catch (BusinessException exception) {
                    LOGGER.log(Level.SEVERE,"Modify Command: Error while modifying", exception);
                }
                print(out, "Priority was modified\n");
            }
            else {
                print(out, "Priority not valid\n");
            }
        }
        else if(numeric && args.length == 4 && args[1].equals("due:")){
            int taskIndex = Integer.parseInt(args[0]);
            String date = args[2] + " " + args[3];
            boolean validDate = date.matches("\\d{4}\\/\\d{2}\\/\\d{2}\\s\\d{2}:\\d{2}:\\d{2}");
            if(validDate){
                Task task = new Task();
                task.setId(taskIndex - 1);
                task.setDue(date);

                try {
                    bo.modifyTask(task);
                } catch (BusinessException exception) {
                    LOGGER.log(Level.SEVERE, "Modify Command: Error while modifying", exception);
                }
            }
            else{
                print(out, "This date is not valid\n");
            }
        }
        else if (!numeric && args.length == 2) {
            print(out, "Command not valid\n");
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