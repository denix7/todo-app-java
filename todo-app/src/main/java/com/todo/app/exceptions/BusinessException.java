package com.todo.app.exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException (String message, Throwable throwable) {
        super (message, throwable);
    }
}
