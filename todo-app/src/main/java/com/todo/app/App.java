package com.todo.app;

import com.todo.app.aplication.TaskService;
import com.todo.app.command.manager.CommandExecutor;
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
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.run();
    }
}