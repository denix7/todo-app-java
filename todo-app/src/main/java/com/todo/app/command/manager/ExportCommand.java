package com.todo.app.command.manager;

import com.todo.app.aplication.TaskService;

import java.io.OutputStream;

public class ExportCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "export";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, TaskService bo) {
        if (args == null) {
            boolean result = bo.exportAll();
            if (result) {
                print(out, "Tasks exported\n");
            } else {
                print(out, "Tasks could not be exported\n");
            }
        }
    }

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}
