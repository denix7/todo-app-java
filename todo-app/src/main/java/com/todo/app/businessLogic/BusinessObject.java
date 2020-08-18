package com.todo.app.businessLogic;

import com.todo.app.dao.TaskDAO;
import com.todo.app.entities.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class BusinessObject {

    public static TaskDAO taskDAO;

    public BusinessObject() {
        this.taskDAO = new TaskDAO();
    }

    public void addTask(String description, String fileName) {
        UUID id = UUID.randomUUID();
        Task task = new Task(description);

        task.setUuid(id);
        task.setStatus("pending");
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

    /*public void listTasks() {
        taskDAO.listTasks();
    }
    */

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