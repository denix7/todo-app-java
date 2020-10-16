package com.todo.app.command.manager;

import com.todo.app.aplication.TaskService;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class TagsCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "tags";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, TaskService bo) {
        if (args == null) {
            List<String> tags;
            tags = bo.getAllTags();
            for (String key : tags) {
                print(out, key + "\n");
            }
        } else if (args.length == 1 && args[0].equals("+")) {
            Map<String, Integer> tags = null;
            tags = bo.getAllTagsWithQuantity();
            tags.forEach((k, v) -> {
                print(out, k + " : " + v + "\n");
            });
        } else {
            print(out, "Command not found\n");
        }
    }

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}
