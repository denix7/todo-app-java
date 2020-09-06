package com.todo.app.command.manager;

import com.todo.app.businessLogic.BusinessObjectTxtImpl;
import com.todo.app.businessLogic.IBusinessObject;

import java.io.OutputStream;

public interface ICommand {
    public String getName();
    public void execute(String[] args, OutputStream out, IBusinessObject bo);
}