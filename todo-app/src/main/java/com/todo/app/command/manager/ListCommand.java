package com.todo.app.command.manager;

import com.todo.app.businessLogic.BusinessObjectTxtImpl;
import com.todo.app.businessLogic.IBusinessObject;
import com.todo.app.entities.Task;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ListCommand extends AbstractCommand {

    public static final String COMMAND_NAME = "list";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, IBusinessObject bo) {
        ArrayList<Task> tasks = null;

        if(args == null){
            tasks = bo.listTasks();
        }
        else{
            if(args.length == 2 && args[0].equals("tag:")) {
                String tag = args[1];
                tasks = bo.filterByTag(tag);
            }
            if(args.length == 2 && args[0].equals("status:")){
                String status = args[1];
                tasks = bo.filterByStatus(status);
            }
            if(args.length == 2 && args[0].equals("priority:")){
                String priority = args[1];
                tasks = bo.filterByPriority(priority);
            }
            else{
                write(out, "Command not valid\n");
            }
        }

        for (Task current : tasks){
            if(tasks == null){
                write(out, "There are not coincidences\n");
            }
            System.out.println(current.showList());
        }
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
