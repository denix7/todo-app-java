package com.todo.app.businessLogic;

import com.todo.app.dao.TaskDAO;
import com.todo.app.entities.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class BusinessObject {

    public static TaskDAO taskDAO;

    public BusinessObject() {
        this.taskDAO = new TaskDAO();
    }

    public void addTask(String[] args, String fileName) {
        UUID id = UUID.randomUUID();
        Task task = new Task(args[0]);

        task.setUuid(id);
        task.setStatus("pending");

        if(args.length > 1)
            task.setPriority(args[1]);
        else
            task.setPriority("M");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        task.setEntry(dtf.format(now));

        try{
            boolean existFile = taskDAO.exist(fileName);
            taskDAO.save(task, fileName, existFile);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean modifyTask() {
        return true;
    }

    public boolean doneTask() {
        return true;
    }

    public void listTasks(String[] args, String fileName) {
        ArrayList<Task> tasks = taskDAO.loadTasks(fileName);
        if(args != null)
            tasks = filterByTag(tasks, args[0]);

        System.out.println("===================LIST==================");
        for(Task current : tasks)
        {
            System.out.println(current.toString());
        }
    }

    public ArrayList<Task> filterByTag(ArrayList<Task> tasks, String tag) {
        ArrayList<Task> answer = new ArrayList<Task>();

        for(Task current : tasks)
        {
            String[] tags = current.getTags();
            for (String currentTag : tags)
            {
                if(currentTag.equals(tag))
                {
                    answer.add(current);
                }
            }
        }
        return answer;
    }

    public void loadFile(String fileName)
    {
        try{
            if(taskDAO.exist(fileName))
            {
                taskDAO.deleteFile(fileName);
                taskDAO.createFile(fileName);
            }
            else {
                taskDAO.deleteFile(fileName);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}