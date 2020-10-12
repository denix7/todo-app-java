package com.todo.app;

import com.todo.app.aplication.TaskService;
import com.todo.app.command.manager.CommandManager;
import com.todo.app.command.manager.Command;
import com.todo.app.dependencyInjection.Injector;
import com.todo.app.util.Fragmenter;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    public static void main(String[] args) {
        final Logger LOGGER = Logger.getLogger(Logger.class.getName());
        System.out.println("===========Welcome to todo app============");
        Scanner scanner = new Scanner(System.in);
        CommandManager commandManager = CommandManager.getInstance();

        while (true) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                Command defaultCommand = commandManager.getCommand("default");
                try {
                    defaultCommand.execute(null, System.out,null);
                } catch (Exception exception) {
                    LOGGER.info("Is not possible to execute the command");
                    LOGGER.log(Level.SEVERE, "Is not possible to execute the command", exception);
                }
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



            TaskService bo  = Injector.getTaskService();
            //Command Executor

            //command.execute(commandArgs2, System.out, new BusinessObjectSQLImpl(new TaskMySqlDAOImpl()));
            command.execute(commandArgs2, System.out, bo);

            }
        }
    }