package com.todo.app.businessLogic;

import com.todo.app.dao.TaskMySqlDAOImpl;
import com.todo.app.entities.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class BusinessObjectSQLImpl implements IBusinessObject {
    public static TaskMySqlDAOImpl taskDAO;

    public BusinessObjectSQLImpl() {
        taskDAO = new TaskMySqlDAOImpl();
    }

    @Override
    public void addTask(String[] args, String fileName) {
        UUID id = UUID.randomUUID();
        Task task = new Task(args[0]);

        task.setUuid(id);
        task.setStatus("pending");
        task.setTag("default");

        if(args.length > 1)
        {
            task.setPriority(args[1]);
        }
        else
        {
            task.setPriority("M");
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        task.setEntry(dtf.format(now));

        try{
            taskDAO.save(task, "", false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void modifyTask(String[] args, String fileName) {
        ArrayList tasks = taskDAO.loadTasks("");
        modifyTaskByIndex(tasks, args);
    }

    public void modifyTaskByIndex(ArrayList<Task> tasks, String[] args) {
        boolean numeric = true;
        numeric = args[0].matches("-?\\d+(\\.\\d+)?");
        Task current;
        if (numeric && args.length == 2) {
            int taskIndex = Integer.parseInt(args[0]) - 1;
            String newDescription = args[1];
            current = tasks.get(taskIndex);
            current.setDescription(newDescription);
        } else {
            int index = Integer.parseInt(args[0]) - 1;
            String newTag = args[2];
            current = tasks.get(index);
            current.setTag(newTag);
        }
        taskDAO.update(current);
    }

    @Override
    public void doneTask(String[] args, String fileName) {

    }

    @Override
    public void listTasks(String[] args, String fileName) {
        ArrayList<Task> tasks =  taskDAO.loadTasks("");

        for(Task current : tasks){
            System.out.println(current);
        }
    }

    @Override
    public ArrayList<Task> filterByTag(ArrayList<Task> tasks, String tag) {
        return null;
    }

    @Override
    public void loadFile(String fileName) {

    }
}
