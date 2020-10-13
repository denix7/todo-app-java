package com.todo.app.exceptions;

public class CommandException extends RuntimeException {
    public CommandException (String message, Throwable throwable) {
        super (message, throwable);
    }

    public CommandException (String message) {
        super (message);
    }
}