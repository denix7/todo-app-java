package com.todo.app.command.manager;

import com.todo.app.businessLogic.BusinessObject;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

public class TagsCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "tags";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObject bo) {
        if(args == null) {
            ArrayList<String> tags = bo.getAllTags();
            for(String key : tags){
                write(out, key + "\n");
            }
        }
        else if(args.length == 1 && args[0].equals("+")) {
            Map<String, Integer> tags = bo.getAllTagsWithQuantity();
            tags.forEach((k, v) -> {
                write(out, k + " : " + v + "\n");
            });
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
