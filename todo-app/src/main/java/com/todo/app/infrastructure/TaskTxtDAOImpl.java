package com.todo.app.infrastructure;

import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.BusinessException;
import com.todo.app.filters.Filter;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskTxtDAOImpl{
    private String fileName;
    private boolean exist;
    private static final Logger LOGGER = Logger.getLogger(TaskMySqlDAOImpl.class.getName());

    public TaskTxtDAOImpl(String fileName) {
        this.fileName = fileName;
        this.exist = exist(fileName);
    }

    public boolean exist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public void save(Task task) {
        File file = new File(fileName);

        try {
            PrintWriter out = new PrintWriter(new FileWriter(file, exist));
            out.println(task.toString());
            out.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void saveList(ArrayList<Task> tasks) {
        File file = new File(fileName);

        try {
            PrintWriter out = new PrintWriter(new FileWriter(file));
            for (Task task: tasks) {
                out.println(task.toString());
            }
            out.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Task task) {

    }

    public void delete(Task task) {

    }

    public ArrayList<Task> loadTasks() {
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

    public List<Task> find(Filter filter) throws BusinessException {
        List<Task> tasks;
        List<Task> answer = new ArrayList<>();

        try {
            tasks = loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer", exception);
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }

        for(Task current : tasks) {
            if(filter.satisfies(current)) {
                answer.add(current);
            }
        }
        return answer;
    }

}