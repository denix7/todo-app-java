package com.todo.app.command.manager;

import com.todo.app.businessLogic.BusinessObjectImpl;

import java.io.OutputStream;

public class ExitCommand extends AbstractCommand {

    public static final String COMMAND_NAME = "exit";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObjectImpl bo, String fileName) {
        write(out, "Bye!");
        System.exit(0);
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
