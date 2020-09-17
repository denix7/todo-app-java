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
            bo.addTask(args[0], null);
        }
        if(args == null || args.length == 0) {
            write(out, "You should add a note");
        }
        if(args != null && args.length == 2 ) {
            if(args[1].equals("M") || args[1].equals("H") || args[1].equals("L")){
                bo.addTask(args[0], args[1]);
                write(out, "Task with priority added");
            }
            else {
                write(out,"Task not added, priority only could be: L/M/H");
            }
        }
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
