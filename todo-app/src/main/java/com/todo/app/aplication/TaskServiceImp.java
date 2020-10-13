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
    public List<Task> getAllTasks() throws BusinessException {
        List<Task> tasks;
        try {
            tasks = taskDAO.loadTasks();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer", exception);
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }
        return tasks;
    }

    @Override
    public List<Task> find(Filter filter) throws BusinessException {
        List<Task> tasks;

        try {
            tasks = taskDAO.find(filter);
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer", exception);
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }

        return tasks;
    }

    @Override
    public int countTasks() throws BusinessException {
        int cantity;

        try {
            cantity = taskDAO.count();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer", exception);
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }

        return cantity;
    }

    @Override
    public int countTasksByFilter(Filter filter) throws BusinessException {
        int cantity;

        try {
            cantity = taskDAO.countByFilter(filter);
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while loading tasks in Business Layer", exception);
            throw new BusinessException("Error. Unable to load tasks in Business Layer", exception);
        }

        return cantity;
    }

    @Override
    public List<String> getAllTags() throws BusinessException {
        List<Task> tasks;
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
        List<Task> tasks;
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

    private Map<String, Integer> countTags(List<Task> tasks) {
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
        List<Task> tasks;
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
        List<Task> tasks;
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
        List<Task> tasks;
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

    private boolean exportAsCsv(List<Task> tasks) throws IOException {
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