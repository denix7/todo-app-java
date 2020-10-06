package com.todo.app.command.manager;

import com.todo.app.aplication.BusinessObject;
import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.BusinessException;
import com.todo.app.exceptions.CommandException;
import com.todo.app.filters.Filter;
import com.todo.app.filters.PriorityFilter;
import com.todo.app.filters.StatusFilter;
import com.todo.app.filters.TagFilter;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;

public class ListCommand extends AbstractCommand {

    public static final String COMMAND_NAME = "list";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObject bo) {
        ArrayList<Task> tasks = null;

        if(args == null){
            try {
                tasks = bo.listTasks();
            } catch (BusinessException exception) {
                LOGGER.log(Level.SEVERE, "List Command: Error while storing", exception);
            }
        }
        else if(args != null && args.length > 2){
            print(out, "Command not found\n");
        }
        else if (args.length == 2 ){
            if(args[0].equals("tag:")) {
                String tag = args[1];
                Filter filter = new TagFilter(tag);
                try {
                    tasks = bo.filter(filter);
                } catch (Exception exception) {
                    LOGGER.log(Level.SEVERE, "List Command: Error while reading", exception);
                }
            }
            else if(args[0].equals("status:")){
                String status = args[1];
                Filter filter = new StatusFilter(status);
                try {
                    tasks = bo.filter(filter);
                } catch (BusinessException exception) {
                    LOGGER.log(Level.SEVERE, "List Command: Error while reading", exception);
                }
            }
            else if(args[0].equals("priority:")){
                String priority = args[1];
                Filter filter = new PriorityFilter(priority);
                try {
                    tasks = bo.filter(filter);
                } catch (Exception exception) {
                    LOGGER.log(Level.SEVERE, "List Command: Error while reading", exception);
                }
            }
            else{
                print(out, "Filter no valid\n");
            }
        }
        else{
            print(out, "Command not valid\n");
        }

        if(tasks.isEmpty()){
            print(out, "There are not coincidences\n");
        }
        else{
            for (Task current : tasks){
                print(out, current.showList()+"\n");
            }
        }
    }

    /*
    @Override
    public void execute(String[] args, OutputStream out, IBusinessObject bo) {
        ArrayList<Task> tasks = null;

        if(args == null){
            tasks = bo.listTasks();
        }
        else if(args != null && args.length > 2){
            write(out, "Command not found\n");
        }
        else if (args.length == 2 ){
            if(args[0].equals("tag:")) {
                String tag = args[1];
                tasks = bo.filterByTag(tag);
            }
            if(args[0].equals("status:")){
                String status = args[1];
                tasks = bo.filterByStatus(status);
            }
            if(args[0].equals("priority:")){
                String priority = args[1];
                tasks = bo.filter(filter);
            }
        }
        else{
            write(out, "Command not valid\n");
        }

        if(tasks == null){
            write(out, "There are not coincidences\n");
        }
        else{
            for (Task current : tasks){
                write(out, current.showList()+"\n");
            }
        }
    }*/

    @Override
    public void print(OutputStream stream, String message) {
        super.print(stream, message);
    }
}
