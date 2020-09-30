package com.todo.app.command.manager;

import com.todo.app.aplication.BusinessObject;

import java.io.OutputStream;

public class AbstractCommand implements Command {


    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObject bo) {

    }

    public void write(OutputStream stream, String message){
        try {
            stream.write(message.getBytes());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
