package com.todo.app.command.manager;

import com.todo.app.aplication.TaskService;
import com.todo.app.domain.entities.Task;
import com.todo.app.filters.Filter;
import com.todo.app.filters.PriorityFilter;
import com.todo.app.filters.StatusFilter;
import com.todo.app.filters.TagFilter;

import java.io.OutputStream;
import java.util.List;

public class ListCommand extends AbstractCommand {

    public static final String COMMAND_NAME = "list";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, TaskService bo) {
        List<Task> tasks = null;

        if (args == null) {
            tasks = bo.getAllTasks();
        } else if (args != null && args.length > 2) {
            print(out, "Command not found\n");
        } else if (args.length == 2) {
            if (args[0].equals("tag:")) {
                String tag = args[1];
                Filter filter = new TagFilter(tag);
                tasks = bo.find(filter);
            } else if (args[0].equals("status:")) {
                String status = args[1];
                Filter filter = new StatusFilter(status);
                tasks = bo.find(filter);
            } else if (args[0].equals("priority:")) {
                String priority = args[1];
                Filter filter = new PriorityFilter(priority);
                tasks = bo.find(filter);
            } else {
                print(out, "Filter no valid\n");
            }
        } else {
            print(out, "Command not valid\n");
        }

        if (tasks == null) {
            print(out, "There are not coincidences\n");
        } else {
            int index = 0;
            for (Task current : tasks) {
                index++;
                print(out, index + " " + current.showList() + "\n");
            }
        }
    }

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}
