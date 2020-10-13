package com.todo.app.infrastructure;

import com.todo.app.domain.entities.Task;
import com.todo.app.exceptions.BusinessException;
import com.todo.app.filters.Filter;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskTxtDAOImpl implements TaskDAO{
    private String fileName;
    private boolean exist;
    private static final Logger LOGGER = Logger.getLogger(TaskMySqlDAOImpl.class.getName());

    public TaskTxtDAOImpl(String fileName) {
        //fileName = "c:\\tasks-java\\tasks.txt";
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

    @Override
    public void saveList(List<Task> tasks) {

    }

    @Override
    public void update(UUID id, Task task) {

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

    @Override
    public Task read(int index) {
        return null;
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

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int countByFilter(Filter filter) {
        return 0;
    }

    /*Service layer*/

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
            save(task);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void modifyTask(String[] args) {
        ArrayList<Task> tasks = loadTasks();
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
        catch(Exception e) {
            e.printStackTrace();
        }
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
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void listTasks(String[] args) {
        ArrayList<Task> tasks = loadTasks();

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
            if(exist(fileName)) {
                deleteFile(fileName);
                createFile(fileName);
            }
            else {
                deleteFile(fileName);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}