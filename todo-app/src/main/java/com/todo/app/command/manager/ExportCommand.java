package com.todo.app.command.manager;

import com.todo.app.aplication.BusinessObject;

import java.io.OutputStream;

public class ExportCommand extends AbstractCommand {
    public static final String COMMAND_NAME = "export";

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String[] args, OutputStream out, BusinessObject bo) {
        if(args == null){
            try{
                boolean result = bo.exportAll();
                write(out, "Tasks exported\n");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(args.length == 1 || args.length > 2) {
            write(out, "Command not valid\n");
        }
        /*else {
            String filter = args[0];
            String arg = args[1];
            ArrayList<Task> filters = new ArrayList<>();
            for(Task current : tasks) {
                if(filter.equals("tag:") && current.getTag().equals(arg)) {
                    filters.add(current);
                }
                if(filter.equals("status:") && current.getStatus().equals(arg)) {
                    filters.add(current);
                }
                if(filter.equals("priority:") && current.getPriority().equals(arg)) {
                    filters.add(current);
                }
            }

            try {
                exportAsCsv(filters);
                System.out.println("Tasks exported in " + getPath());
            }
            catch (Exception e) {
                e.printStackTrace();
        }*/
    }

    @Override
    public void write(OutputStream stream, String message) {
        super.write(stream, message);
    }
}
