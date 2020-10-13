package com.todo.app.command.manager;

import com.todo.app.aplication.TaskService;
import com.todo.app.exceptions.BusinessException;
import com.todo.app.exceptions.CommandException;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class TagsCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "tags";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, TaskService bo) {
        if(args == null) {
            List<String> tags = null;
            try {
                tags = bo.getAllTags();
            } catch (BusinessException exception) {
                LOGGER.log(Level.SEVERE, "Tag Command: Error while reading", exception);
                throw new CommandException("Error. Unable execute the tags command", exception);
            }
            for(String key : tags){
                print(out, key + "\n");
            }
        }
        else if(args.length == 1 && args[0].equals("+")) {
            Map<String, Integer> tags = null;
            try {
                tags = bo.getAllTagsWithQuantity();
            } catch (BusinessException exception) {
                LOGGER.log(Level.SEVERE, "Tag Command: Error while reading", exception);
                throw new CommandException("Error. Unable execute the tags command", exception);
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
