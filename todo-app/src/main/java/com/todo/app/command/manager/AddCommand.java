package com.todo.app.command.manager;

import com.todo.app.businessLogic.BusinessObject;

import java.io.OutputStream;
import java.util.Arrays;

public class AddCommand extends AbstractCommand{

    public static final String COMMAND_NAME = "add";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObject bo, String fileName) {
        if(args != null && args.length == 1)
        {
            System.out.println(Arrays.toString(args));
            write(out, "Adding element with title");
            bo.addTask(args[0], fileName);
        }
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
