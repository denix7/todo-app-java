package com.todo.app;

import com.todo.app.aplication.BusinessObjectSQLImpl;
import com.todo.app.command.manager.CommandManager;
import com.todo.app.command.manager.Command;
import com.todo.app.infrastructure.TaskMySqlDAOImpl;
import com.todo.app.util.Fragmenter;

import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("===========Welcome to todo app============");
        Scanner scanner = new Scanner(System.in);
        CommandManager commandManager = CommandManager.getInstance();

        while (true) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                Command defaultCommand = commandManager.getCommand("default");
                defaultCommand.execute(null, System.out,null);
                continue;
            }

            String[] commandArgs = Fragmenter.fragment(line);
            String commandName = commandArgs[0];
            String[] commandArgs2 = null;

            if (commandArgs.length > 1) {
                commandArgs2 = Arrays.copyOfRange(commandArgs, 1, commandArgs.length);
            }

            Command command = commandManager.getCommand(commandName);

            //Business Object
            //BusinessObject bo = new BusinessObjectTxtImpl(new TaskTxtDAOImpl("c:\\\\tasks-java\\\\tasks.txt"));
            //BusinessObject bo = new BusinessObjectSQLImpl(new TaskMySqlDAOImpl());

            //Command Executor
            command.execute(commandArgs2, System.out, new BusinessObjectSQLImpl(new TaskMySqlDAOImpl()));
        }
    }
}