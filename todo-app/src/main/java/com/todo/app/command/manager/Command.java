package com.todo.app.command.manager;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public interface Command {
    public String getName();
    public void execute(String[] args, OutputStream out);

    class CommandManager {
        private static CommandManager commandManager;
        private static Map<String, Class<? extends Command>> commands = new HashMap<String, Class<? extends Command>>();

        private CommandManager() {
            register(ExitCommand.COMMAND_NAME, ExitCommand.class);
        }

        public static CommandManager getInstance() {
            if(commandManager == null)
            {
                commandManager = new CommandManager();
            }
            return commandManager;
        }

        public void register(String commandName, Class<? extends Command> command) {
            commands.put(commandName, command);
        }

        public Command getCommand(String command) {
            if(!commands.containsKey(command))
            {
                //return new DefaultCommand();
                return null;
            }

            try{
                Command iCommand = commands.get(command).newInstance();
                return iCommand;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }
}