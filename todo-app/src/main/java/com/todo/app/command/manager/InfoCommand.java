package com.todo.app.command.manager;

import com.todo.app.businessLogic.IBusinessObject;
import com.todo.app.entities.Task;

import java.io.OutputStream;
import java.util.Arrays;

public class InfoCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "info";
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, IBusinessObject bo) {
        //System.out.println(Arrays.toString(args));
        if(args == null) {
            write(out, "Command not valid\n");
        }
        else if(args.length == 1){
            String indexExpected = args[0];
            boolean isNumeric = indexExpected.matches("-?\\d+(\\.\\d+)?");

            if (isNumeric) {
                int index = Integer.parseInt(indexExpected) - 1;
                String taskInfo = bo.getInfo(index);
                write(out, taskInfo);
            }
            else{
                System.out.println("The param is incorrect\n");
            }
        }
        else {
            write(out, "Command not valid\n");
        }
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
