package com.todo.app.command.manager;

import com.todo.app.aplication.BusinessObject;

import java.io.OutputStream;

public interface Command {
    public String getName();
    public void execute(String[] args, OutputStream out, BusinessObject bo);
}