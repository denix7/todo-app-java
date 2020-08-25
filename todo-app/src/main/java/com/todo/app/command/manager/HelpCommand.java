package com.todo.app.command.manager;

import com.todo.app.businessLogic.BusinessObjectTxtImpl;
import com.todo.app.businessLogic.IBusinessObject;

import java.io.OutputStream;

public class HelpCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "help";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, IBusinessObject bo, String fileName) {
        String message =
                "todo exit                         : Close the terminal\n" +
                "todo add \"task name\"            : Register a new task\n" +
                "todo add \"task name\" priority:H : Register a new task with priority High\n" +
                "todo list                         : Obtain all the tasks from txt file\n" +
                "todo list tag:default             : Obtain all the tasks with tag\n" +
                "todo done 1                       : Mark as done a task with it index\n" +
                "todo done tag:something           : Mark as done all the tasks with the tag\n" +
                "todo modify 1 \"new description\" : Modify a task given the index\n" +
                "todo modify 1 tag: \"default\"    : Modify de tag of a task given the index\n";
        write(out, message);
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
