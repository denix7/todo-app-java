package com.todo.app.command.manager;

import com.todo.app.aplication.BusinessObject;

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
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}
