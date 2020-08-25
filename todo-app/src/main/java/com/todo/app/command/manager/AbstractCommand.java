package com.todo.app.command.manager;

import com.todo.app.businessLogic.BusinessObjectTxtImpl;
import com.todo.app.businessLogic.IBusinessObject;

import java.io.OutputStream;

public class AbstractCommand implements ICommand {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(String[] args, OutputStream out, IBusinessObject bo, String fileName) {

    }

    public void write(OutputStream stream, String message){
        try
        {
            stream.write(message.getBytes());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
