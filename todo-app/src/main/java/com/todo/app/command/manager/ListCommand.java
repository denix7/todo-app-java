package com.todo.app.command.manager;

import com.todo.app.businessLogic.IBusinessObject;
import com.todo.app.entities.Task;
import com.todo.app.filters.Filter;
import com.todo.app.filters.PriorityFilter;
import com.todo.app.filters.StatusFilter;
import com.todo.app.filters.TagFilter;

import java.io.OutputStream;
import java.util.ArrayList;

public class ListCommand extends AbstractCommand {

    public static final String COMMAND_NAME = "list";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

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
                Filter filter = new TagFilter(tag);
                tasks = bo.filter(filter);
            }
            else if(args[0].equals("status:")){
                String status = args[1];
                Filter filter = new StatusFilter(status);
                tasks = bo.filter(filter);
            }
            else if(args[0].equals("priority:")){
                String priority = args[1];
                Filter filter = new PriorityFilter(priority);
                tasks = bo.filter(filter);
            }
            else{
                write(out, "Filter no valid\n");
            }
        }
        else{
            write(out, "Command not valid\n");
        }

        if(tasks.isEmpty()){
            write(out, "There are not coincidences\n");
        }
        else{
            for (Task current : tasks){
                write(out, current.showList()+"\n");
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
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
