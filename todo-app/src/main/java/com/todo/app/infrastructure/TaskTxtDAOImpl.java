package com.todo.app.infrastructure;

import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.PersistentException;
import com.todo.app.filters.Filter;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class TaskTxtDAOImpl implements TaskDAO{
    private String fileName;
    private boolean exist;
    private static final Logger LOGGER = Logger.getLogger(TaskMySqlDAOImpl.class.getName());

    public TaskTxtDAOImpl() {
        this.fileName = "c:\\tasks-java\\tasks.txt";
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
        catch(IOException exception) {
            throw new PersistentException("Error. Unable to save task in Persistence file", exception);
        }
    }

    @Override
    public void update(UUID id, Task task) {
        ArrayList<Task> tasks = loadTasks();
        modifyTaskByIndex(tasks, task);
    }

    public void modifyTaskByIndex(ArrayList<Task> tasks, Task task) {
        System.out.println(task.getId());
        int taskIndex = task.getId();
        String newDescription = task.getDescription();
        Task current = tasks.get(taskIndex);
        current.setDescription(newDescription);

        try {
            boolean existFile = exist(fileName);
            if(existFile) {
                deleteFile(fileName);
                saveList(tasks);
                System.out.println("Task " + " was modified");
            }
            else {
                System.out.println("Doesn't exists any file");
            }
        }
        catch(Exception exception) {
            throw new PersistentException("Error. Unable to save task in Persistence file", exception);
        }
    }

    @Override
    public void saveList(List<Task> tasks) {
        File file = new File(fileName);

        try {
            PrintWriter out = new PrintWriter(new FileWriter(file));
            for (Task task: tasks) {
                out.println(task.toString());
            }
            out.close();
        }
        catch(IOException exception) {
            throw new PersistentException("Error. Unable to save task in Persistence file", exception);
        }
    }

    public boolean delete(UUID id) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean deleteByFilter(Filter filter) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Task read(UUID id) {
        return null;
    }

    public ArrayList<Task> loadTasks() {
        File file = new File(fileName);
        ArrayList<Task> tasks = new ArrayList<Task>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
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
        catch (IOException exception) {
            throw new PersistentException("Error. Unable to lpad tasks in Persistence file", exception);
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
        catch (Exception exception) {
            throw new PersistentException("Error. Unable to create file in Persistence file", exception);
        }
    }

    public void deleteFile(String fileName) {
        File file = new File(fileName);
        file.delete();
    }

    public List<Task> find(Filter filter) {
        List<Task> tasks;
        List<Task> answer = new ArrayList<>();

        try {
            tasks = loadTasks();
        } catch (Exception exception) {
            throw new PersistentException("Error. Unable to load tasks in Persistence file", exception);
        }

        for(Task current : tasks) {
            if(filter.satisfies(current)) {
                answer.add(current);
            }
        }
        return answer;
    }

    @Override
    public int count() {
        ArrayList<Task> tasks = loadTasks();
        return tasks.size();
    }

    @Override
    public int countByFilter(Filter filter) {
        List<Task> tasks;
        List<Task> answer = new ArrayList<>();

        try {
            tasks = loadTasks();
        } catch (Exception exception) {
            throw new PersistentException("Error. Unable to load tasks in Persistence file", exception);
        }

        for(Task current : tasks) {
            if(filter.satisfies(current)) {
                answer.add(current);
            }
        }
        return answer.size();
    }

    public void doneTask(String[] args) {
        ArrayList<Task> tasks = loadTasks();
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
            boolean existFile = exist(fileName);
            if(existFile) {
                deleteFile(fileName);
                saveList(tasks);
                System.out.println("marked as done");
            }
            else {
                System.out.println("Doesn't exists any file");
            }
        }
        catch(Exception exception) {
            throw new PersistentException("Error. Unable to mark as done task in Persistence file", exception);
        }
    }

    public void listTasks(String[] args) {
        ArrayList<Task> tasks = loadTasks();

        if(args != null) {
            tasks = filterByTag(tasks, args[0]);
        }

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
            if(exist(fileName)) {
                deleteFile(fileName);
                createFile(fileName);
            }
            else {
                deleteFile(fileName);
            }
        }
        catch (Exception exception) {
            throw new PersistentException("Error. Unable to load file in Persistence file", exception);
        }
    }
}