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
    public void execute(String[] args, OutputStream out, IBusinessObject bo) {
        if(args != null && args.length == 1) {
            write(out, "Adding element with title");
            bo.addTask(args);
        }
        if(args == null || args.length == 0) {
            write(out, "You should add a note");
        }
        if(args != null && args.length == 2 ) {
            System.out.println("Task with priority added");
            bo.addTask(args);
        }
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
