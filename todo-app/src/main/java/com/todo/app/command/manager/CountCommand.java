package com.todo.app.command.manager;

import com.todo.app.aplication.TaskService;
import com.todo.app.filters.Filter;
import com.todo.app.filters.PriorityFilter;
import com.todo.app.filters.StatusFilter;
import com.todo.app.filters.TagFilter;

import java.io.OutputStream;

public class CountCommand extends AbstractCommand {

    public static final String COMMAND_NAME = "count";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, TaskService bo) {
        int result = 0;
        Filter filter;

        if (args == null) {
            result = bo.countTasks();
            print(out, "There are : " + result + " tasks founded\n");
        } else if (args.length == 2) {
            String value = args[1];
            if (args[0].equals("priority:")) {
                if (value.equals("H") || value.equals("M") || value.equals("L")) {
                    filter = new PriorityFilter(value);
                    result = bo.countTasksByFilter(filter);
                }
            } else if (args[0].equals("status:")) {
                filter = new StatusFilter(value);
                result = bo.countTasksByFilter(filter);
            } else if (args[0].equals("tag:")) {
                filter = new TagFilter(value);
                result = bo.countTasksByFilter(filter);
            } else {
                print(out, "Params not valid\n");
            }

            print(out, "There are : " + result + " tasks founded\n");
        } else {
            print(out, "Command not found\n");
        }
    }

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}