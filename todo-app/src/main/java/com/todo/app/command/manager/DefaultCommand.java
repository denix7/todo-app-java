package com.todo.app.command.manager;

import com.todo.app.businessLogic.BusinessObject;

import java.io.OutputStream;

public class DefaultCommand extends AbstractCommand{

    public static final String COMMAND_NAME = "default";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObject bo, String fileName) {
        write(out, "Command not found");
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
