package com.todo.app.command.manager;

import com.todo.app.businessLogic.BusinessObjectTxtImpl;
import com.todo.app.businessLogic.IBusinessObject;
import com.todo.app.dao.ITaskDAO;
import com.todo.app.dao.TaskMySqlDAOImpl;
import com.todo.app.entities.Task;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ModifyCommand extends AbstractCommand {
    ITaskDAO taskDAO = new TaskMySqlDAOImpl();

    public static final String COMMAND_NAME = "modify";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, IBusinessObject bo) {
        System.out.println("modify : "  + Arrays.toString(args));
        //bo.modifyTask(args);
        boolean numeric;
        numeric = args[0].matches("-?\\d+(\\.\\d+)?");
        if(args == null || args.length == 1 || args.length > 5) {
            write(out, "Command not valid");
        }
        else if(args.length == 3 || args.length == 2) {
            //ArrayList tasks = taskDAO.loadTasks();
            if (numeric && args.length == 2) {
                int taskIndex = Integer.parseInt(args[0]);
                String newDescription = args[1];
                bo.modifyTask(taskIndex - 1, newDescription, null, null);
            }
        }
        else if (args.length == 5) {
            int taskIndex = Integer.parseInt(args[0]);
            String tag = args[2];
            String priority = args[4];
            System.out.println(taskIndex);
            System.out.println(tag);
            System.out.println(priority);
            bo.modifyTask(taskIndex - 1, "", tag, priority);
        }
        else if (!numeric && args.length == 2) {
            write(out, "Command not valid");
        }
        else {
            write(out, "Command not found");
        }
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
