package com.todo.app.dao;

import com.todo.app.entities.Task;

import java.io.*;
import java.util.*;

public class TaskTxtDAOImpl implements ITaskDAO{

    public TaskTxtDAOImpl() {
    }

    public boolean exist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    @Override
    public void save(Task task, String fileName, boolean exist) {
        File file = new File(fileName);

        try
        {
            PrintWriter out = new PrintWriter(new FileWriter(file, exist));
            out.println(task.toString());
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void saveList(ArrayList<Task> tasks, String fileName, boolean exist) {
        File file = new File(fileName);

        try
        {
            PrintWriter out = new PrintWriter(new FileWriter(file));
            for (Task task: tasks)
            {
                out.println(task.toString());
            }
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void delete(Task task) {

    }

    @Override
    public ArrayList<Task> loadTasks(String fileName) {
        File file = new File(fileName);
        ArrayList<Task> tasks = new ArrayList<Task>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String description = "";
            String line;

            while((line = reader.readLine()) != null) {
                String data[];
                ArrayList<String> arr = new ArrayList<>();

                data = line.split(",");
                String aux = "";

                Task current = new Task(data[0], data[1], UUID.fromString(data[2]), data[3], data[4], data[5]);
                tasks.add(current);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void createFile(String fileName) {
        File file = new File(fileName);
        try{
            PrintWriter out = new PrintWriter(file);
            out.close();
            System.out.println("Se ha creado el archivo");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String fileName) {
        File file = new File(fileName);
        file.delete();
    }
}
