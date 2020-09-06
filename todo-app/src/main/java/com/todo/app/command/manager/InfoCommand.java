package com.todo.app.command.manager;

import com.todo.app.businessLogic.IBusinessObject;

import java.io.OutputStream;

public class InfoCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "info";
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, IBusinessObject bo) {
        bo.getInfo(args);
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
