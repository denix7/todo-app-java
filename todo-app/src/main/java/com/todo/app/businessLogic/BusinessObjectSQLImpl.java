package com.todo.app.businessLogic;

import com.todo.app.dao.ITaskDAO;
import com.todo.app.dao.TaskMySqlDAOImpl;
import com.todo.app.entities.Task;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BusinessObjectSQLImpl implements IBusinessObject {
    public static ITaskDAO taskDAO;

    public BusinessObjectSQLImpl() {
        taskDAO = new TaskMySqlDAOImpl();
    }

    @Override
    public void addTask(Task task) {
        try {
            taskDAO.save(task);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void modifyTask(int index, String description, String tag, String priority) {
        /*if(args == null || args.length == 1 || args.length > 3) {
            System.out.println("Command not valid");
        }
        else if(args.length == 3 || args.length == 2) {
            modifyTaskByIndex(tasks, index, description, tag, priority);
        }
        else {
            System.out.println("Command not found");
        }*/
        ArrayList tasks = taskDAO.loadTasks();
        modifyTaskByIndex(tasks, index, description, tag, priority);
    }

    public void modifyTaskByIndex(ArrayList<Task> tasks, int index, String description, String tag, String priority) {
        Task current;
        current = tasks.get(index);
        current.setDescription(description);

        if (priority != null) {
            current.setPriority(priority);
        }
        if(tag != null) {
            current.setTag(tag);
        }

        try{
            if(current != null){
                taskDAO.update(current);
                System.out.println("task was modified");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void doneTask(String[] args) {
        ArrayList<Task> tasks = taskDAO.loadTasks();
        markAsDone(tasks, args[0]);
    }

    public void markAsDone(ArrayList<Task> tasks, String arg) {
        boolean numeric;
        numeric = arg.matches("-?\\d+(\\.\\d+)?");
        Task task = null;

        if (numeric) {
            int index = Integer.parseInt(arg);
            task = tasks.get(index - 1);
            task.setStatus("completed");
        }
        else {
            for (Task current : tasks) {
                if (current.getTag().equals(arg)) {
                    current.setStatus("completed");
                }
            }
        }

        try {
            taskDAO.saveList(tasks);
            System.out.println("marked as done");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listTasks(String[] args) {
        ArrayList<Task> tasks =  taskDAO.loadTasks();

        if(args != null) {
            tasks = filterByTag(tasks, args[0]);
        }

        System.out.println("===================LIST==================");

        int indexTask = 1;
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

    @Override
    public int countTasks(String param){
        ArrayList<Task> tasks =  taskDAO.loadTasks();
        ArrayList<Task> result = new ArrayList<Task>();

        if(param.equals("")) {
            return tasks.size();
        }

        for (Task current : tasks){
            if(param.equals(current.getPriority()) || param.equals(current.getStatus()) || param.equals(current.getTag())){
                result.add(current);
            }
        }
        return result.size();
    }

    @Override
    public void getTags(String[] args) {
        ArrayList<Task> tasks =  taskDAO.loadTasks();
        HashMap<String, Integer> tags = new HashMap<>();
        if(args == null) {
            tags = countTags(tasks);
            for(String key : tags.keySet()){
                System.out.println(key);
            }
        }
        else if(args.length == 1 && args[0].equals("+")) {
            tags = countTags(tasks);
            tags.forEach((k, v) -> {
                System.out.println(k + " : " + v);
            });
        }
        else {
            System.out.println("Command not found");
        }
    }

    private HashMap<String, Integer> countTags(ArrayList<Task> tasks) {
        HashMap<String, Integer> tags = new HashMap<>();
        int i=1;
        for (Task current : tasks) {
            if(!tags.containsKey(current.getTag())){
                tags.put(current.getTag(), i);
            }
            else {
                int cantity = tags.get(current.getTag());
                cantity++;
                tags.put(current.getTag(), cantity);
            }
        }
        return tags;
    }

    @Override
    public boolean deleteTask(int index) {
        ArrayList<Task> tasks = taskDAO.loadTasks();

        if(index <= tasks.size()){
            Task taskToDelete = tasks.get(index);
            taskDAO.delete(taskToDelete);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String getInfo(int index) {
        ArrayList<Task> tasks = taskDAO.loadTasks();
        Task current = tasks.get(index);

        return ("Name:     " + current.getDescription() + "\n" +
                "ID:       " + current.getUuid() + "\n" +
                "Status:   " + current.getStatus() + "\n" +
                "Tag:      " + current.getTag() + "\n" +
                "priority: " + current.getPriority() + "\n" +
                "entry :   " + current.getEntry() + "\n"
        );
    }

    @Override
    public void export(String[] args) {
        ArrayList<Task> tasks = taskDAO.loadTasks();

        if(args == null){
            try{
                exportAsCsv(tasks);
                System.out.println("Tasks exported in " + getPath());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(args.length == 1 || args.length > 2) {
            System.out.println("Command not valid");
        }
        else {
            String filter = args[0];
            String arg = args[1];
            ArrayList<Task> filters = new ArrayList<>();
            for(Task current : tasks) {
                if(filter.equals("tag:") && current.getTag().equals(arg)) {
                    filters.add(current);
                }
                if(filter.equals("status:") && current.getStatus().equals(arg)) {
                    filters.add(current);
                }
                if(filter.equals("priority:") && current.getPriority().equals(arg)) {
                    filters.add(current);
                }
            }

            try {
                exportAsCsv(filters);
                System.out.println("Tasks exported in " + getPath());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void exportAsCsv(ArrayList<Task> tasks) throws IOException {
        String path = getPath() + "\\tasks.csv";

        FileWriter out = new FileWriter(path);
        String[] HEADERS = { "Title", "Status", "UUID", "Entry", "Priority", "Tag"};

        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            for(Task task : tasks) {
                printer.printRecord(task);
            }
        }
    }

    @Override
    public void config(String[] args) {
        if(args == null) {
            System.out.println("Command not found");
        }
        else if(args.length == 2) {
            String path = args[1];
            setPath(path);
        }
        else {
            System.out.println("Command not valid");
        }
    }

    private void setPath(String path) {
        try (OutputStream output = new FileOutputStream("src\\\\main\\\\resources\\\\META-INF\\\\path.properties")) {
            Properties properties = new Properties();
            properties.setProperty("path.config", path);
            properties.store(output, null);
            System.out.println("Path changed");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPath() {
        try(InputStream input = new FileInputStream("src\\\\main\\\\resources\\\\META-INF\\\\path.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            String customProperty = properties.getProperty("path.config");

            return customProperty;

        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}