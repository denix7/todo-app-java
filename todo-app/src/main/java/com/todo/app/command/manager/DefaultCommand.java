package com.todo.app.command.manager;

import com.todo.app.aplication.BusinessObject;

import java.io.OutputStream;

public class DefaultCommand extends AbstractCommand{

    public static final String COMMAND_NAME = "default";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObject bo) {
        print(out, "Command not found\n");
    }

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}
