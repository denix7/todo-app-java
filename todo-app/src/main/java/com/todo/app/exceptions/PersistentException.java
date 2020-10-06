package com.todo.app.exceptions;

public class PersistentException extends RuntimeException {
    public PersistentException(String mesagge, Throwable throwable) {
        super(mesagge, throwable);
    }
}