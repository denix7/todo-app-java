package com.todo.app.command.manager;

import com.todo.app.businessLogic.BusinessObjectImpl;

import java.io.OutputStream;

public class DoneCommand extends AbstractCommand {

    public static final String COMMAND_NAME = "done";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObjectImpl bo, String fileName) {
        bo.doneTask(args, fileName);
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
