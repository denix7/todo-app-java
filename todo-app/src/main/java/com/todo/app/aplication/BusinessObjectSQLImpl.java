package com.todo.app.aplication;

import com.todo.app.exceptions.BusinessException;
import com.todo.app.exceptions.PersistentException;
import com.todo.app.infrastructure.TaskDAO;
import com.todo.app.domain.entities.Task;
import com.todo.app.filters.Filter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BusinessObjectSQLImpl implements BusinessObject {
    public static TaskDAO taskDAO;
    private static final Logger LOGGER = Logger.getLogger(BusinessObjectSQLImpl.class.getName());

    public BusinessObjectSQLImpl(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public void addTask(Task task) throws BusinessException {
        try {
            taskDAO.save(task);
        }
        catch (PersistentException exception){
            LOGGER.log(Level.SEVERE, "Error while storing in Business Layer");
            throw new BusinessException("Error. Unable to add in Business Layer", exception);
        }
    }

    @Override
    public void modifyTask(Task newTask) throws BusinessException {
        ArrayList<Task> tasks = null;
        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer");
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }

        Task current;
        current = (Task) tasks.get(newTask.getId());
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
            }
        }
        catch (PersistentException exception){
            LOGGER.log(Level.SEVERE, "Error while modifiying in Business Layer");
            throw new BusinessException("Error. Unable to modify in Business Layer", exception);
        }
    }

    @Override
    public void doneTask(Task task) throws BusinessException {
        ArrayList<Task> tasks = null;
        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer");
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }

        Task newTask = tasks.get(task.getId());

        if(task.getTag() == null){
            newTask.setStatus(task.getStatus());
            try {
                taskDAO.update(newTask);
            }
            catch (PersistentException exception){
                LOGGER.log(Level.SEVERE, "Error while done task in Business Layer");
                throw new BusinessException("Error. Unable to mark as done in Business Layer", exception);
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
            catch (PersistentException exception){
                LOGGER.log(Level.SEVERE, "Error while done task in Business Layer");
                throw new BusinessException("Error. Unable to mark as done in Business Layer", exception);
            }
        }
    }

    @Override
    public ArrayList<Task> listTasks() throws BusinessException {
        ArrayList<Task> tasks = null;
        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer");
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }
        return tasks;
    }

    @Override
    public ArrayList<Task> filterByTag(String tag) throws BusinessException {
        ArrayList<Task> tasks = null;
        ArrayList<Task> answer = new ArrayList<>();

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer");
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }

        for(Task current : tasks) {
            String currentTag = current.getTag();
            if(currentTag.equals(tag)) {
                answer.add(current);
            }
        }
        return answer;
    }

    @Override
    public int countTasks(String param) throws BusinessException {
        ArrayList<Task> tasks = null;
        ArrayList<Task> result = new ArrayList<>();

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer");
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }

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
    public ArrayList<Task> filterByStatus(String status) throws BusinessException {
        ArrayList<Task> tasks = null;
        ArrayList<Task> answer = new ArrayList<>();

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer");
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }


        for(Task current : tasks) {
            String currentStatus = current.getStatus();
            if(currentStatus.equals(status)) {
                answer.add(current);
            }
        }
        return answer;
    }

    @Override
    public ArrayList<Task> filterByPriority(String priority) throws BusinessException {
        ArrayList<Task> tasks = null;
        ArrayList<Task> answer = new ArrayList<>();

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer");
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }

        for(Task current : tasks) {
            String currentPriority = current.getPriority();
            if(currentPriority.equals(priority)) {
                answer.add(current);
            }
        }
        return answer;
    }

    @Override
    public ArrayList<Task> filter(Filter filter) throws BusinessException {
        ArrayList<Task> tasks = null;
        ArrayList<Task> answer = new ArrayList<>();

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer");
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
    public ArrayList<String> getAllTags() throws BusinessException {
        ArrayList<Task> tasks = null;
        Map<String, Integer> tags;

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer");
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }

        tags = countTags(tasks);
        return new ArrayList<>(tags.keySet());
    }

    @Override
    public Map<String, Integer> getAllTagsWithQuantity() throws BusinessException {
        ArrayList<Task> tasks = null;
        Map<String, Integer> tags;

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer");
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }

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
    public boolean deleteTask(int index) throws BusinessException {
        ArrayList<Task> tasks = null;
        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer");
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }

        if(index <= tasks.size()){
            Task taskToDelete = tasks.get(index);
            try {
                taskDAO.delete(taskToDelete);
            }
            catch (PersistentException exception){
                LOGGER.log(Level.SEVERE, "Error while storing in Business Layer");
                throw new BusinessException("Error in Businnes Layer", exception);
            }
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String getInfo(int index) throws BusinessException {
        ArrayList<Task> tasks = null;
        Task current = tasks.get(index);

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer");
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }

        return ("Name:     " + current.getDescription() + "\n" +
                "ID:       " + current.getUuid() + "\n" +
                "Status:   " + current.getStatus() + "\n" +
                "Tag:      " + current.getTag() + "\n" +
                "priority: " + current.getPriority() + "\n" +
                "entry :   " + current.getEntry() + "\n"
        );
    }

    @Override
    public boolean exportAll() throws BusinessException {
        ArrayList<Task> tasks = null;
        boolean result;

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer");
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }
        try{
            result = exportAsCsv(tasks);
        }
        catch (IOException exception){
            LOGGER.log(Level.SEVERE, "Error while exporting file in Business Layer");
            result = false;
            exception.printStackTrace();
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
        catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Error while setting file path in Business Layer");
            exception.printStackTrace();
        }
    }

    private String getPath() {
        try(InputStream input = new FileInputStream("src\\\\main\\\\resources\\\\META-INF\\\\path.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            return properties.getProperty("path.config");

        }
        catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Error while getting file path in Business Layer");
            exception.printStackTrace();
            return null;
        }
    }
}