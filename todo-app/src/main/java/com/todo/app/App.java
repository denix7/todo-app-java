package com.todo.app;

import com.todo.app.businessLogic.BusinessObjectSQLImpl;
import com.todo.app.businessLogic.BusinessObjectTxtImpl;
import com.todo.app.businessLogic.IBusinessObject;
import com.todo.app.command.manager.CommandManager;
import com.todo.app.command.manager.ICommand;
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
                ICommand defaultCommand = commandManager.getCommand("default");
                defaultCommand.execute(null, System.out,null);
                continue;
            }

            String[] commandArgs = Fragmenter.fragment(line);
            String commandName = commandArgs[0];
            String[] commandArgs2 = null;

            if (commandArgs.length > 1) {
                commandArgs2 = Arrays.copyOfRange(commandArgs, 1, commandArgs.length);
            }

            ICommand command = commandManager.getCommand(commandName);

            //Business Object
            //IBusinessObject bo = new BusinessObjectTxtImpl();
            IBusinessObject bo = new BusinessObjectSQLImpl();

            //Command Executor
            command.execute(commandArgs2, System.out, bo);
        }
    }
}