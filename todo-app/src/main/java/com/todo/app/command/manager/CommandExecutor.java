package com.todo.app.command.manager;

import com.todo.app.aplication.TaskService;
import com.todo.app.dependencyInjection.Injector;
import com.todo.app.exceptions.CommandException;
import com.todo.app.util.Fragmenter;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandExecutor {
    final Logger LOGGER = Logger.getLogger(Logger.class.getName());

    public void run() {
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

            TaskService bo  = Injector.getTaskService();

            try {
                command.execute(commandArgs2, System.out, bo);
            } catch (CommandException exception) {
                LOGGER.log(Level.SEVERE, "Command: Error while executing command", exception);
            }
        }
    }
}
