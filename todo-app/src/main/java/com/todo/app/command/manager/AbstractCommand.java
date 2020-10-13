package com.todo.app.command.manager;

import com.todo.app.aplication.TaskService;
import com.todo.app.exceptions.CommandException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(AbstractCommand.class.getName());

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(String[] args, OutputStream out, TaskService bo) {

    }

    public void print(OutputStream stream, String message) {
        try {
            stream.write(message.getBytes());
        }
        catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Abstract command exception", exception);
        }
    }
}
