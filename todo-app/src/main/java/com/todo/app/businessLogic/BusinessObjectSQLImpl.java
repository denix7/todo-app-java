package com.todo.app.businessLogic;

import com.todo.app.dao.TaskMySqlDAOImpl;
import com.todo.app.entities.Task;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BusinessObjectSQLImpl implements IBusinessObject {
    public static TaskMySqlDAOImpl taskDAO;

    public BusinessObjectSQLImpl() {
        taskDAO = new TaskMySqlDAOImpl();
    }

    @Override
    public void addTask(String[] args, String fileName) {
        UUID id = UUID.randomUUID();
        Task task = new Task(args[0]);

        task.setUuid(id);
        task.setStatus("pending");
        task.setTag("default");

        if(args.length > 1) {
            task.setPriority(args[1]);
        }
        else {
            task.setPriority("M");
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        task.setEntry(dtf.format(now));

        try{
            taskDAO.save(task, "", false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void modifyTask(String[] args, String fileName) {
        if(args != null) {
            ArrayList tasks = taskDAO.loadTasks("");
            modifyTaskByIndex(tasks, args);
        }
        else {
            System.out.println("Command not found");
        }
    }

    public void modifyTaskByIndex(ArrayList<Task> tasks, String[] args) {
        boolean numeric = true;
        numeric = args[0].matches("-?\\d+(\\.\\d+)?");
        Task current;
        if (numeric && args.length == 2) {
            int taskIndex = Integer.parseInt(args[0]);
            String newDescription = args[1];
            current = tasks.get(taskIndex - 1);
            current.setDescription(newDescription);
        }
        else {
            int index = Integer.parseInt(args[0]) - 1;
            String arg = args[2];
            current = tasks.get(index);
            if(arg.equals("H") || arg.equals("M")  || arg.equals("L") ){
                current.setPriority(arg);
            }
            else{
                current.setTag(arg);
            }
        }

        try{
            taskDAO.update(current);
            System.out.println("task was modified");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void doneTask(String[] args, String fileName) {
        ArrayList<Task> tasks = taskDAO.loadTasks(fileName);
        markAsDone(tasks, fileName, args[0]);
    }

    public void markAsDone(ArrayList<Task> tasks, String fileName, String arg) {
        boolean numeric = true;
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

        try{
            taskDAO.saveList(tasks, "", false);
            System.out.println("marked as done");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void listTasks(String[] args, String fileName) {
        ArrayList<Task> tasks =  taskDAO.loadTasks("");

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

    @Override
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
    public void countTasks(String[] args, String fileName){
        ArrayList<Task> tasks =  taskDAO.loadTasks("");

        if(args == null){
            System.out.println("There are : " + tasks.size() + " tasks founded");
        }
        else if(args.length == 2) {
            ArrayList<Task> result = new ArrayList<Task>();
            String arg = args[1];
            for (Task current : tasks){
                if(arg.equals(current.getPriority()) || arg.equals(current.getStatus()) || arg.equals(current.getTag())){
                    result.add(current);
                }
            }
            System.out.println("There are : " + result.size() + " tasks founded");
        }
        else{
            System.out.println("Command not found");
        }
    }

    @Override
    public void getTags(String[] args, String fileName){
        ArrayList<Task> tasks =  taskDAO.loadTasks("");
        HashMap<String, Integer> tags = new HashMap<>();
        if(args == null){
            tags = countTags(tasks);
            for(String key : tags.keySet()){
                System.out.println(key);
            }
        }
        else if(args.length == 1 && args[0].equals("+")){
            tags = countTags(tasks);
            tags.forEach((k, v) -> {
                System.out.println(k + " : " + v);
            });
        }
        else{
            System.out.println("Command not found");
        }
    }

    private HashMap<String, Integer> countTags(ArrayList<Task> tasks){
        HashMap<String, Integer> tags = new HashMap<>();
        int i=1;
        for (Task current : tasks){
            if(!tags.containsKey(current.getTag())){
                tags.put(current.getTag(), i);
            }
            else{
                int cantity = tags.get(current.getTag());
                cantity++;
                tags.put(current.getTag(), cantity);
            }
        }
        return tags;
    }

    @Override
    public void deleteTask(String[] args, String fileName){
        ArrayList<Task> tasks = taskDAO.loadTasks("");
        String arg = args[0];
        boolean isNumeric = arg.matches("-?\\d+(\\.\\d+)?");

        if(args == null){
            System.out.println("Command not found");
        }
        if(isNumeric){
            int index = Integer.parseInt(args[0]) - 1;
            if(index <= tasks.size()){
                Task taskToDelete = tasks.get(index);
                taskDAO.delete(taskToDelete);
                System.out.println("Task deleted");
            }
            else{
                System.out.println("This element doesn't exist");
            }
        }
        else{
            arg = args[1];
            for(Task current : tasks){
                if(current.getTag().equals(arg) || current.getStatus().equals(arg) || current.getPriority().equals(arg)){
                    taskDAO.delete(current);
                }
            }
            System.out.println("Task deleted");
        }
    }

    @Override
    public void getInfo(String[] args, String fileName) {
        ArrayList<Task> tasks = taskDAO.loadTasks("");

        if(args == null){
            System.out.println("Command not found");
        }
        else {
            String arg = args[0];
            boolean isNumeric = arg.matches("-?\\d+(\\.\\d+)?");

            if (isNumeric) {
                int index = Integer.parseInt(arg) - 1;
                if(index < tasks.size()) {
                    Task current = tasks.get(index);

                    System.out.println("Name:     " + current.getDescription() + "\n" +
                            "ID:       " + current.getUuid() + "\n" +
                            "Status:   " + current.getStatus() + "\n" +
                            "Tag:      " + current.getTag() + "\n" +
                            "priority: " + current.getPriority() + "\n" +
                            "entry :   " + current.getEntry() + "\n"
                    );
                }
                else{
                    System.out.println("the task doesn't exist");
                }
            }
        }
    }

    @Override
    public void export(String[] args, String fileName) {
        ArrayList<Task> tasks = taskDAO.loadTasks("");

        if(args == null){
            System.out.println("Command not found");
        }
        else if(args.length == 1 || args.length > 2){
            System.out.println("Command not valid");
        }
        else{
            String filter = args[0];
            String arg = args[1];
            ArrayList<Task> filters = new ArrayList<>();
            for(Task current : tasks){
                if(filter.equals("tag:") && current.getTag().equals(arg)){
                    filters.add(current);
                }
                if(filter.equals("status:") && current.getStatus().equals(arg)){
                    filters.add(current);
                }
                if(filter.equals("priority:") && current.getPriority().equals(arg)){
                    filters.add(current);
                }
            }

            try{
                exportAsCsv(filters);
                System.out.println("Tasks exported in " + getPath());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void exportAsCsv(ArrayList<Task> tasks) throws IOException {
        String path = getPath() + "\\tasks.csv";

        FileWriter out = new FileWriter(path);
        String[] HEADERS = { "Title", "Status", "UUID", "Entry", "Priority", "Tag"};

        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))){
            for(Task task : tasks){
                printer.printRecord(task);
            }
        }
    }

    public void config(String[] args, String fileName) {
        if(args == null){
            System.out.println("Command not found");
        }
        else if(args.length == 2){
            String path = args[1];
            setPath(path);
        }
        else{
            System.out.println("Command not valid");
        }
    }

    private void setPath(String path){
        try (OutputStream output = new FileOutputStream("src\\\\main\\\\resources\\\\META-INF\\\\path.properties")){
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
        try(InputStream input = new FileInputStream("src\\\\main\\\\resources\\\\META-INF\\\\path.properties")){
            Properties properties = new Properties();
            properties.load(input);
            String customProperty = properties.getProperty("path.config");

            return customProperty;

        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}