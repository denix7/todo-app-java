package com.todo.app.businessLogic;

import com.todo.app.dao.TaskTxtDAOImpl;
import com.todo.app.entities.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class BusinessObjectTxtImpl implements IBusinessObject {

    public static TaskTxtDAOImpl taskDAO;

    public BusinessObjectTxtImpl() {
        this.taskDAO = new TaskTxtDAOImpl();
    }

    public void addTask(String[] args, String fileName) {
        UUID id = UUID.randomUUID();
        Task task = new Task(args[0]);

        task.setUuid(id);
        task.setStatus("pending");
        task.setTag("default");

        if(args.length > 1) {
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
            boolean existFile = taskDAO.exist(fileName);
            taskDAO.save(task, fileName, existFile);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyTask(String[] args, String fileName) {
        ArrayList<Task> tasks = taskDAO.loadTasks(fileName);
        modifyTaskByIndex(tasks, fileName, args);
    }

    public void modifyTaskByIndex(ArrayList<Task> tasks, String fileName, String[] args) {
        try {
            boolean numeric = true;
            numeric = args[0].matches("-?\\d+(\\.\\d+)?");

            if (numeric && args.length == 2) {
                int taskIndex = Integer.parseInt(args[0]) - 1;
                String newDescription = args[1];
                Task current = tasks.get(taskIndex);
                current.setDescription(newDescription);
            }
            else {
                int index = Integer.parseInt(args[0]) - 1;
                String newTag = args[2];
                Task current = tasks.get(index);
                current.setTag(newTag);
            }
        }
        catch (Exception e) {
            System.out.println("Parameters is needed");
        }

        try
        {
            boolean existFile = taskDAO.exist(fileName);
            if(existFile) {
                taskDAO.deleteFile(fileName);
                taskDAO.saveList(tasks, fileName, existFile);
                System.out.println("Task " + " was modified");
            }
            else {
                System.out.println("Doesn't exists any file");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void doneTask(String[] args, String fileName) {
        ArrayList<Task> tasks = taskDAO.loadTasks(fileName);
        markAsDone(tasks, fileName, args[0]);
    }

    public void markAsDone(ArrayList<Task> tasks, String fileName, String arg) {
        boolean isNumeric = true;
        isNumeric = arg.matches("-?\\d+(\\.\\d+)?");

        if(isNumeric) {
            int index = Integer.parseInt(arg);
            Task current = tasks.get(index-1);
            current.setStatus("completed");
        }
        else {
            for(Task current : tasks) {
                if(current.getTag().equals(arg)) {
                    current.setStatus("completed");
                }
            }
        }

        try
        {
            boolean existFile = taskDAO.exist(fileName);
            if(existFile) {
                taskDAO.deleteFile(fileName);
                taskDAO.saveList(tasks, fileName, existFile);
                System.out.println("marked as done");
            }
            else {
                System.out.println("Doesn't exists any file");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void listTasks(String[] args, String fileName) {
        ArrayList<Task> tasks = taskDAO.loadTasks(fileName);

        if(args != null) {
            tasks = filterByTag(tasks, args[0]);
        }

        System.out.println("===================LIST==================");
        int indexTask= 1;
        for(Task current : tasks) {
            System.out.println(indexTask +")" + " " + current.showList());
            indexTask++;
        }
        System.out.println("There are : " + tasks.size() + " elements");
    }

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

    public void loadFile(String fileName) {
        try {
            if(taskDAO.exist(fileName)) {
                taskDAO.deleteFile(fileName);
                taskDAO.createFile(fileName);
            }
            else {
                taskDAO.deleteFile(fileName);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void countTasks(String[] args, String fileName) {

    }

    @Override
    public void getTags(String[] args, String fileName) {

    }

    @Override
    public void deleteTask(String[] args, String fileName) {

    }

    @Override
    public void getInfo(String[] args, String fileName) {

    }
}