package com.todo.app;

import com.todo.app.command.manager.CommandExecutor;
public class App {
    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.run();

  }
}