package com.todo.app.command.manager;

import com.todo.app.businessLogic.BusinessObjectTxtImpl;
import com.todo.app.businessLogic.IBusinessObject;

import java.io.OutputStream;

public class AddCommand extends AbstractCommand{

    public static final String COMMAND_NAME = "add";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, IBusinessObject bo, String fileName) {
        if(args != null && args.length == 1)
        {
            write(out, "Adding element with title");
            bo.addTask(args, fileName);
        }
        if(args == null || args.length == 0)
        {
            write(out, "Debe agreagar una nota");
        }
        if(args != null && args.length == 2 )
        {
            System.out.println("Task with priority added");
            bo.addTask(args, fileName);
        }
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
