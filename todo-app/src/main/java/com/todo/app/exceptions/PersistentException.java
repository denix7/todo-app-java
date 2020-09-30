package com.todo.app.exceptions;

public class PersistentException extends Exception {
    public PersistentException(String mesagge, Throwable throwable) {
        super(mesagge, throwable);
    }
}