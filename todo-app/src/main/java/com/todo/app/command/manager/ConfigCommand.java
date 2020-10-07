package com.todo.app.command.manager;

import com.todo.app.aplication.BusinessObject;
import com.todo.app.exceptions.CommandException;

import java.io.OutputStream;

public class ConfigCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "config";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObject bo) {
        if(args == null) {
            print(out, "Command not found\n");
        }
        else if(args.length == 2) {
            String path = args[1];
            boolean isValidPath = path.matches("^[a-zA-Z]:\\\\(((?![<>:\"/\\\\|?*]).)+((?<![ .])\\\\)?)*$");
            if(isValidPath) {
                bo.config(path);
            } else {
                print(out, "El path no es valido\n");
            }
        }
        else {
            print(out, "Command not valid\n");
        }
    }

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}
