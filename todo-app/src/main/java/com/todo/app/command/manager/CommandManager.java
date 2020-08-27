package com.todo.app.command.manager;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static CommandManager commandManager;
    private static Map<String, Class<? extends ICommand>> commands = new HashMap<String, Class<? extends ICommand>>();

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
    }

    public static CommandManager getInstance() {
        if(commandManager == null) {
            commandManager = new CommandManager();
        }
        return commandManager;
    }

    public void register(String commandName, Class<? extends ICommand> command) {
        commands.put(commandName, command);
    }

    public ICommand getCommand(String command) {
        if(!commands.containsKey(command)) {
            return new DefaultCommand();
        }

        try{
            ICommand iCommand = commands.get(command).newInstance();
            return iCommand;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}