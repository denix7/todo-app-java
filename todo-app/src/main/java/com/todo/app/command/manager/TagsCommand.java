package com.todo.app.command.manager;

import com.todo.app.aplication.BusinessObject;
import com.todo.app.exceptions.BusinessException;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;

public class TagsCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "tags";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObject bo) {
        if(args == null) {
            ArrayList<String> tags = null;
            try {
                tags = bo.getAllTags();
            } catch (BusinessException exception) {
                LOGGER.log(Level.SEVERE, "Tag Command: Error while storing", exception);
                exception.printStackTrace();
            }
            for(String key : tags){
                print(out, key + "\n");
            }
        }
        else if(args.length == 1 && args[0].equals("+")) {
            Map<String, Integer> tags = null;
            try {
                tags = bo.getAllTagsWithQuantity();
            } catch (Exception exception) {
                LOGGER.log(Level.SEVERE, "Tag Command: Error while storing", exception);
                exception.printStackTrace();
            }
            tags.forEach((k, v) -> {
                print(out, k + " : " + v + "\n");
            });
        }
        else {
            print(out, "Command not found\n");
        }
    }

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}
