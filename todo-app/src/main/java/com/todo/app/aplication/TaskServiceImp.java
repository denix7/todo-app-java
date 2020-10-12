package com.todo.app.aplication;

import com.todo.app.dependencyInjection.Injector;
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

public class TaskServiceImp implements TaskService {
    public static TaskDAO taskDAO;
    private static final Logger LOGGER = Logger.getLogger(TaskServiceImp.class.getName());

    public TaskServiceImp() {
        this.taskDAO = Injector.getTaskDao();
    }

    public TaskServiceImp(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public void addTask(Task task) throws BusinessException {
        try {
            taskDAO.save(task);
        }
        catch (PersistentException exception){
            LOGGER.log(Level.SEVERE, "Error while storing in Business Layer", exception);
            throw new BusinessException("Error. Unable to add in Business Layer", exception);
        }
    }

    @Override
    public void modifyTask(UUID id, Task newTask) throws BusinessException {
        try {
            taskDAO.update(id, newTask);
        } catch (PersistentException exception){
            LOGGER.log(Level.SEVERE, "Error while modifying in Business Layer", exception);
            throw new BusinessException("Error. Unable to modify in Business Layer", exception);
        }
    }

    @Override
    public Task getTaskById(UUID id) {
        try {
            return taskDAO.read(Integer.parseInt(id.toString()));
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while getting task by id tasks in Business Layer", exception);
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }
    }

    @Override
    public ArrayList<Task> getAllTasks() throws BusinessException {
        ArrayList<Task> tasks;
        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer", exception);
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }
        return tasks;
    }

    @Override
    public ArrayList<Task> filterByTag(String tag) throws BusinessException {
        ArrayList<Task> tasks;
        ArrayList<Task> answer = new ArrayList<>();

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer", exception);
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
        ArrayList<Task> tasks;
        ArrayList<Task> result = new ArrayList<>();

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer", exception);
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
        ArrayList<Task> tasks;
        ArrayList<Task> answer = new ArrayList<>();

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer", exception);
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
        ArrayList<Task> tasks;
        ArrayList<Task> answer = new ArrayList<>();

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer", exception);
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
        ArrayList<Task> tasks;
        ArrayList<Task> answer = new ArrayList<>();

        try {
            tasks = taskDAO.loadTasks();
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
    public ArrayList<String> getAllTags() throws BusinessException {
        ArrayList<Task> tasks;
        Map<String, Integer> tags;

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer", exception);
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }

        tags = countTags(tasks);
        return new ArrayList<>(tags.keySet());
    }

    @Override
    public Map<String, Integer> getAllTagsWithQuantity() throws BusinessException {
        ArrayList<Task> tasks;
        Map<String, Integer> tags;

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer", exception);
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
        ArrayList<Task> tasks;
        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer", exception);
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }

        if(index <= tasks.size()){
            Task taskToDelete = tasks.get(index);
            try {
                taskDAO.delete(taskToDelete);
            }
            catch (PersistentException exception){
                LOGGER.log(Level.SEVERE, "Error while storing in Business Layer", exception);
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
        ArrayList<Task> tasks;
        Task current = null;
        try {
            current = taskDAO.read(index);
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer", exception);
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        } finally {
            if (current != null) {
                return ("Name:     " + current.getDescription() + "\n" +
                        "ID:       " + current.getUuid() + "\n" +
                        "Status:   " + current.getStatus() + "\n" +
                        "Tag:      " + current.getTag() + "\n" +
                        "priority: " + current.getPriority() + "\n" +
                        "entry :   " + current.getEntry() + "\n"
                );
            }
            return  null;
        }
    }

    @Override
    public boolean exportAll() throws BusinessException {
        ArrayList<Task> tasks;
        boolean result = false;

        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer", exception);
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }
        try{
            result = exportAsCsv(tasks);
        }
        catch (IOException exception){
            LOGGER.log(Level.SEVERE, "Error while exporting file in Business Layer", exception);
            throw new BusinessException("Error. Unable to exports tasks in Business Layer", exception);
        } finally {
            return result;
        }
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
    public void config(String newPath) {
        setPath(newPath);
    }

    private void setPath(String path) {
        try (OutputStream output = new FileOutputStream("src\\\\main\\\\resources\\\\META-INF\\\\path.properties")) {
            Properties properties = new Properties();
            properties.setProperty("path.config", path);
            properties.store(output, null);
            System.out.println("Path changed");
        }
        catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Error while setting file path in Business Layer", exception);
        }
    }

    private String getPath() {
        Properties properties = null;
        try(InputStream input = new FileInputStream("src\\\\main\\\\resources\\\\META-INF\\\\path.properties")) {
            properties = new Properties();
            properties.load(input);
        }
        catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Error while getting file path in Business Layer", exception);
        } finally {
            return properties.getProperty("path.config");
        }
    }
}