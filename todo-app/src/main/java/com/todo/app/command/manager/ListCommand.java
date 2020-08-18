package com.todo.app.command.manager;

import java.io.OutputStream;

public class ListCommand extends AbstractCommand {

    public static final String COMMAND_NAME = "list";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out) {
        super.execute(args, out);
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
