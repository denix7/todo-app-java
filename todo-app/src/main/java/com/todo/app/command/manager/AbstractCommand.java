package com.todo.app.command.manager;

import com.todo.app.businessLogic.BusinessObjectImpl;

import java.io.OutputStream;

public class AbstractCommand implements Command {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObjectImpl bo, String fileName) {

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
