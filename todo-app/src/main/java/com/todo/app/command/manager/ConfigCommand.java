package com.todo.app.command.manager;

import com.todo.app.businessLogic.BusinessObject;

import java.io.OutputStream;

public class ConfigCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "config";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObject bo) {
        bo.config(args);
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
