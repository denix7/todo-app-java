package com.todo.app.command.manager;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static CommandManager commandManager;
    private static Map<String, Class<? extends Command>> commands = new HashMap<String, Class<? extends Command>>();

    private CommandManager() {
        register(ExitCommand.COMMAND_NAME, ExitCommand.class);
        register(AddCommand.COMMAND_NAME, AddCommand.class);
        register(ModifyCommand.COMMAND_NAME, ModifyCommand.class);
        register(ListCommand.COMMAND_NAME, ListCommand.class);
        register(DoneCommand.COMMAND_NAME, DoneCommand.class);
        register(HelpCommand.COMMAND_NAME, HelpCommand.class);
        register(CountCommand.COMMAND_NAME, CountCommand.class);
        register(TagsCommand.COMMAND_NAME, TagsCommand.class);
        register(DeleteCommand.COMMAND_NAME, DeleteCommand.class);
        register(InfoCommand.COMMAND_NAME, InfoCommand.class);
        register(ExportCommand.COMMAND_NAME, ExportCommand.class);
        register(ConfigCommand.COMMAND_NAME, ConfigCommand.class);
    }

    public static CommandManager getInstance() {
        if(commandManager == null) {
            commandManager = new CommandManager();
        }
        return commandManager;
    }

    public void register(String commandName, Class<? extends Command> command) {
        commands.put(commandName, command);
    }

    public Command getCommand(String commandName) {
        if(!commands.containsKey(commandName)) {
            return new DefaultCommand();
        }

        try{
            Command command = commands.get(commandName).newInstance();
            return command;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new DefaultCommand();
        }
    }
}