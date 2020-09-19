package com.todo.app.command.manager;

import com.todo.app.businessLogic.IBusinessObject;
import java.io.OutputStream;

public class CountCommand extends AbstractCommand {

    public static final String COMMAND_NAME = "count";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, IBusinessObject bo) {
        int result = 0;

        if(args == null){
            result = bo.countTasks("");
            write(out, "There are : " + result + " tasks founded\n");
        }
        else if(args.length == 2) {
            String filter = args[0];
            String value = args[1];

            if(filter.equals("priority:")){
                if(value.equals("H") || value.equals("M") || value.equals("L")) {
                    result = bo.countTasks(value);
                }
            }
            else if(filter.equals("status:")) {
                result = bo.countTasks(value);
            }
            else if(filter.equals("tag:")) {
                result = bo.countTasks(value);
            }
            else {
                write(out, "Params not valid\n");
            }

            write(out, "There are : " + result + " tasks founded\n");
        }
        else {
            write(out, "Command not found\n");
        }
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
