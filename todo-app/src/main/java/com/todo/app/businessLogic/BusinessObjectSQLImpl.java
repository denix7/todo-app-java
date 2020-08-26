package com.todo.app.businessLogic;

import com.todo.app.dao.TaskMySqlDAOImpl;
import com.todo.app.entities.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

        if(args.length > 1) {
            task.setPriority(args[1]);
        }
        else {
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

    @Override
    public void modifyTask(String[] args, String fileName) {
        if(args != null) {
            ArrayList tasks = taskDAO.loadTasks("");
            modifyTaskByIndex(tasks, args);
        }
        else {
            System.out.println("Command not found");
        }
    }

    public void modifyTaskByIndex(ArrayList<Task> tasks, String[] args) {
        boolean numeric = true;
        numeric = args[0].matches("-?\\d+(\\.\\d+)?");
        Task current;
        if (numeric && args.length == 2) {
            int taskIndex = Integer.parseInt(args[0]);
            String newDescription = args[1];
            current = tasks.get(taskIndex - 1);
            current.setDescription(newDescription);
        }
        else {
            int index = Integer.parseInt(args[0]) - 1;
            String arg = args[2];
            current = tasks.get(index);
            if(arg.equals("H") || arg.equals("M")  || arg.equals("L") ){
                current.setPriority(arg);
            }
            else{
                current.setTag(arg);
            }
        }

        try{
            taskDAO.update(current);
            System.out.println("task was modified");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void doneTask(String[] args, String fileName) {
        ArrayList<Task> tasks = taskDAO.loadTasks(fileName);
        markAsDone(tasks, fileName, args[0]);
    }

    public void markAsDone(ArrayList<Task> tasks, String fileName, String arg) {
        boolean numeric = true;
        numeric = arg.matches("-?\\d+(\\.\\d+)?");
        Task task = null;

        if (numeric) {
            int index = Integer.parseInt(arg);
            task = tasks.get(index - 1);
            task.setStatus("completed");
        }
        else {
            for (Task current : tasks) {
                if (current.getTag().equals(arg)) {
                    current.setStatus("completed");
                }
            }
        }

        try{
            taskDAO.saveList(tasks, "", false);
            System.out.println("marked as done");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void listTasks(String[] args, String fileName) {
        ArrayList<Task> tasks =  taskDAO.loadTasks("");

        if(args != null) {
            tasks = filterByTag(tasks, args[0]);
        }

        System.out.println("===================LIST==================");

        for(Task current : tasks) {
            System.out.println(current.getId() +")" + " " + current.showList());
        }
        System.out.println("There are : " + tasks.size() + " elements");
    }

    @Override
    public ArrayList<Task> filterByTag(ArrayList<Task> tasks, String tag) {
        ArrayList<Task> answer = new ArrayList<Task>();

        for(Task current : tasks) {
            String currentTag = current.getTag();
            if(currentTag.equals(tag)) {
                answer.add(current);
            }
        }
        if(answer.isEmpty()) {
            System.out.println("Nothing founded");
        }
        return answer;
    }

    @Override
    public void countTasks(String[] args, String fileName){
        System.out.println(Arrays.toString(args));
        if(args == null){
            ArrayList<Task> tasks =  taskDAO.loadTasks("");
            System.out.println("There are : " + tasks.size() + " tasks");
        }
        else{
            System.out.println("Command not found");
        }
    }
}
