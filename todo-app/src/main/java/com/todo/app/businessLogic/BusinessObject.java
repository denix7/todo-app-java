package com.todo.app.businessLogic;

import com.todo.app.dao.TaskDAO;
import com.todo.app.entities.Task;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class BusinessObject {

    public static TaskDAO taskDAO;

    public BusinessObject() {
        this.taskDAO = new TaskDAO();
    }

    public void addTask(String[] args, String fileName) {
        UUID id = UUID.randomUUID();
        Task task = new Task(args[0]);
        //System.out.println("BO " + Arrays.toString(args));

        task.setUuid(id);
        task.setStatus("pending");
        task.setTag("default");

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

    public void modifyTask(String[] args, String fileName) {
        //System.out.println("BO " + Arrays.toString(args));
        ArrayList<Task> tasks = taskDAO.loadTasks(fileName);
        modifyTaskByIndex(tasks, fileName, args);
        System.out.println("modified");
    }

    private void modifyTaskByIndex(ArrayList<Task> tasks, String fileName, String[] args) {
        boolean numeric = true;
        numeric = args[0].matches("-?\\d+(\\.\\d+)?");

        if(numeric && args.length<2) {
            int taskIndex = Integer.parseInt(args[0]) - 1;
            String newDescription = args[1];
            Task current = tasks.get(taskIndex);
            current.setDescription(newDescription);
        }
        else
        {
            int index = Integer.parseInt(args[0]) - 1;
            String newTag = args[2];
            Task current = tasks.get(index);
            current.setTag(newTag);
        }
        /*else
        {
            //System.out.println("es letra");
            String tagName = args[1];
            String dueDate = args[2];
            for(Task current : tasks)
            {
                if(current.equals(tagName))
                {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime due = LocalDateTime.now();
                    current.setDue(due.toString());
                }
            }
        }*/
        try{
            boolean existFile = taskDAO.exist(fileName);
            if(existFile)
            {
                taskDAO.deleteFile(fileName);
                taskDAO.saveList(tasks, fileName, existFile);
                System.out.println("Task " + " was modified");
            }
            else
            {
                System.out.println("Doesn't exists any file");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void doneTask(String[] args, String fileName) {
        ArrayList<Task> tasks = taskDAO.loadTasks(fileName);
        //System.out.println("BO " + Arrays.toString(args));
        String arg = args[0];
        markAsDone(tasks, fileName, args[0]);
        System.out.println();
    }

    public void markAsDone(ArrayList<Task> tasks, String fileName, String arg) {
        //System.out.println(tasks.toString());
        boolean numeric = true;
        numeric = arg.matches("-?\\d+(\\.\\d+)?");
        if(numeric)
        {
            int index = Integer.parseInt(arg);
            Task current = tasks.get(index-1);
            current.setStatus("completed");
        }
        else
        {
            for(Task current : tasks)
            {
                if(current.getTag().equals(arg))
                {
                    current.setStatus("completed");
                }
            }
        }
        //System.out.println(tasks.toString());
        try{
            boolean existFile = taskDAO.exist(fileName);
            if(existFile)
            {
                taskDAO.deleteFile(fileName);
                taskDAO.saveList(tasks, fileName, existFile);
                System.out.println("marked as done");
            }
            else
            {
                System.out.println("Doesn't exists any file");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void listTasks(String[] args, String fileName) {
        ArrayList<Task> tasks = taskDAO.loadTasks(fileName);
        //System.out.println("BO " + Arrays.toString(args));
        if(args != null)
            tasks = filterByTag(tasks, args[0]);

        System.out.println("===================LIST==================");
        //System.out.println(tasks);
        int indexTask= 1;
        for(Task current : tasks)
        {
            System.out.println(indexTask +")" + " " + current.showList());
            indexTask++;
        }
        System.out.println("There are : " + tasks.size() + " elements");

    }

    public ArrayList<Task> filterByTag(ArrayList<Task> tasks, String tag) {
        ArrayList<Task> answer = new ArrayList<Task>();
        for(Task current : tasks)
        {
            String currentTag = current.getTag();
            if(currentTag.equals(tag))
            {
                answer.add(current);
            }
        }
        if(answer.isEmpty())
            System.out.println("Nothing founded");
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