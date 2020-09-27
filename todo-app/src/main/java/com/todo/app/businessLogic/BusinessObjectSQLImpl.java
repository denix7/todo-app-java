package com.todo.app.businessLogic;

import com.todo.app.dao.TaskDAO;
import com.todo.app.entities.Task;
import com.todo.app.filters.Filter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.util.*;

public class BusinessObjectSQLImpl implements BusinessObject {
    public static TaskDAO taskDAO;

    public BusinessObjectSQLImpl(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
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
    public void modifyTask(Task newTask) {
        ArrayList tasks = taskDAO.loadTasks();

        Task current;
        current = (Task)tasks.get(newTask.getId());
        if(newTask.getDescription() != null){
            current.setDescription(newTask.getDescription());
        }
        if (newTask.getPriority() != null) {
            current.setPriority(newTask.getPriority());
        }
        if(newTask.getTag() != null) {
            current.setTag(newTask.getTag());
        }
        if(newTask.getDue() != null) {
            current.setDue(newTask.getDue());
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
    public void doneTask(Task task) {
        ArrayList<Task> tasks = taskDAO.loadTasks();
        Task newTask = tasks.get(task.getId());

        if(task.getTag() == null){
            newTask.setStatus(task.getStatus());
            try {
                taskDAO.update(newTask);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(task.getTag() != null){
            ArrayList<Task> tasksWithTag = new ArrayList<>();
            for (Task current : tasks){
                if(current.getTag().equals(task.getTag())){
                    current.setStatus(task.getStatus());
                    tasksWithTag.add(current);
                }
            }
            try {
                taskDAO.saveList(tasksWithTag);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Task> listTasks() {
        ArrayList<Task> tasks =  taskDAO.loadTasks();
        return tasks;
    }

    @Override
    public ArrayList<Task> filterByTag(String tag) {
        ArrayList<Task> tasks =  taskDAO.loadTasks();
        ArrayList<Task> answer = new ArrayList<Task>();

        for(Task current : tasks) {
            String currentTag = current.getTag();
            if(currentTag.equals(tag)) {
                answer.add(current);
            }
        }
        return answer;
    }

    @Override
    public ArrayList<Task> filterByStatus(String status) {
        ArrayList<Task> tasks =  taskDAO.loadTasks();
        ArrayList<Task> answer = new ArrayList<Task>();

        for(Task current : tasks) {
            String currentStatus = current.getStatus();
            if(currentStatus.equals(status)) {
                answer.add(current);
            }
        }
        return answer;
    }

    @Override
    public ArrayList<Task> filterByPriority(String priority) {
        ArrayList<Task> tasks =  taskDAO.loadTasks();
        ArrayList<Task> answer = new ArrayList<Task>();

        for(Task current : tasks) {
            String currentPriority = current.getPriority();
            if(currentPriority.equals(priority)) {
                answer.add(current);
            }
        }
        return answer;
    }

    @Override
    public ArrayList<Task> filter(Filter filter) {
        ArrayList<Task> tasks =  taskDAO.loadTasks();
        ArrayList<Task> answer = new ArrayList<Task>();

        for(Task current : tasks) {
            if(filter.satisfies(current)) {
                answer.add(current);
            }
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
    public ArrayList<String> getAllTags() {
        ArrayList<Task> tasks =  taskDAO.loadTasks();
        Map<String, Integer> tags = new HashMap<>();

        tags = countTags(tasks);
        return new ArrayList<String>(tags.keySet());
    }

    @Override
    public Map<String, Integer> getAllTagsWithQuantity() {
        ArrayList<Task> tasks =  taskDAO.loadTasks();
        Map<String, Integer> tags = new HashMap<>();

        tags = countTags(tasks);
        return tags;
    }

    private Map<String, Integer> countTags(ArrayList<Task> tasks) {
        Map<String, Integer> tags = new HashMap<>();
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
    public boolean exportAll() {
        ArrayList<Task> tasks = taskDAO.loadTasks();
        boolean result;
        try{
            result = exportAsCsv(tasks);
        }catch (Exception e){
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    private boolean exportAsCsv(ArrayList<Task> tasks) throws IOException {
        String path = getPath() + "\\tasks.csv";
        boolean result;

        FileWriter out = new FileWriter(path);
        String[] HEADERS = { "Title", "Status", "UUID", "Entry", "Priority", "Tag"};

        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            for(Task task : tasks) {
                printer.printRecord(task);
            }
            result = true;
        }
        catch(Exception e){
            result = false;
            e.printStackTrace();
        }
        return result;
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