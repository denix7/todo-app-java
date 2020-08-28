package com.todo.app.command.manager;

import com.todo.app.businessLogic.IBusinessObject;

import java.io.OutputStream;

public class ExportCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "export";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, IBusinessObject bo, String fileName) {
        bo.export(args, fileName);
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
