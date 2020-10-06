package com.todo.app.aplication;

import com.todo.app.infrastructure.TaskTxtDAOImpl;
import com.todo.app.domain.entities.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class BusinessObjectTxtImpl  {

    public static TaskTxtDAOImpl taskDAO;
    private String fileName;

    public BusinessObjectTxtImpl() {

        fileName = "c:\\tasks-java\\tasks.txt";
        taskDAO = new TaskTxtDAOImpl(fileName);
    }

    public void addTask(String taskName, String priority) {
        UUID id = UUID.randomUUID();
        Task task = new Task(taskName);

        task.setUuid(id);
        task.setStatus("pending");
        task.setTag("default");

        if(priority != null) {
            task.setPriority(priority);
        }
        else {
            task.setPriority("M");
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        task.setEntry(dtf.format(now));


        try {
            taskDAO.save(task);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void modifyTask(String[] args) {
        ArrayList<Task> tasks = taskDAO.loadTasks();
        modifyTaskByIndex(tasks, args);
    }

    public void modifyTaskByIndex(ArrayList<Task> tasks, String[] args) {
        try {
            boolean numeric;
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

        try {
            boolean existFile = taskDAO.exist(fileName);
            if(existFile) {
                taskDAO.deleteFile(fileName);
                taskDAO.saveList(tasks);
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

    public void doneTask(String[] args) {
        ArrayList<Task> tasks = taskDAO.loadTasks();
        markAsDone(tasks, args[0]);
    }

    public void markAsDone(ArrayList<Task> tasks, String arg) {
        boolean isNumeric;
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
                taskDAO.saveList(tasks);
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

    public void listTasks(String[] args) {
        ArrayList<Task> tasks = taskDAO.loadTasks();

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
        ArrayList<Task> answer = new ArrayList<>();

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

    public void loadFile() {
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


    public void countTasks(String[] args) {

    }


    public void getTags(String[] args) {

    }


    public void deleteTask(String[] args) {

    }


    public void getInfo(String[] args) {

    }


    public void export(String[] args) {

    }


    public void config(String[] args) {

    }
}