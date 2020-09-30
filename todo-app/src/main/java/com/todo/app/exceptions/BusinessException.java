package com.todo.app.exceptions;

public class BusinessException extends Exception {
    public BusinessException (String message, Throwable throwable) {
        super (message, throwable);
    }
}
