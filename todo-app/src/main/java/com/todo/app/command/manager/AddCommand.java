package com.todo.app.command.manager;

import java.io.OutputStream;
import java.util.Arrays;

public class AddCommand extends AbstractCommand{

    public static final String COMMAND_NAME = "add";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out) {
        if(args != null && args.length == 1)
        {
            System.out.println(Arrays.toString(args));
            write(out, "Adding element with title");
            //taskDao.save(args[0]);
        }
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
