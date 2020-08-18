package com.todo.app.dao;

import com.todo.app.entities.Task;

import java.io.*;
import java.util.*;

public class TaskDAO {

    //HashMap<UUID, Task> bd = new HashMap<UUID, Task>();

    public TaskDAO() {
    }

    public boolean exist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public void save(Task task, String fileName, boolean exist) {
        File file = new File(fileName);

        try{
            PrintWriter out = new PrintWriter(new FileWriter(file, exist));
            out.println(task.toString());
            out.close();
            System.out.println("\nguardado con exito en file!");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean update() {
        return true;
    }

    public ArrayList<Task> getAllTasks(String fileName) {
        File file = new File(fileName);
        ArrayList<Task> tasks = new ArrayList<Task>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String description = "";
            String line = reader.readLine();
            while(line != null)
            {
                Task current = new Task(line);
                tasks.add(current);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return tasks;
    }

    public void createFile(String fileName)
    {
        File file = new File(fileName);
        try{
            PrintWriter out = new PrintWriter(file);
            out.close();
            System.out.println("Se ha creado el archivo");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteFile(String fileName)
    {
        File file = new File(fileName);
        file.delete();
        System.out.println("Se ha eliminado el archivo");
    }
}
