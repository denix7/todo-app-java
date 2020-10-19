package com.todo.app.aplication;

import com.todo.app.dependencyInjection.Injector;
import com.todo.app.exceptions.BusinessException;
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
        this.taskDAO = Injector.getTaskMySqlDao();
    }

    public TaskServiceImp(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public void addTask(Task task) {
        taskDAO.save(task);
    }

    @Override
    public void modifyTask(UUID id, Task newTask) {
        taskDAO.update(id, newTask);
    }

    @Override
    public Task getTaskById(UUID id) {
        return taskDAO.read(id);
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = taskDAO.loadTasks();
        return tasks;
    }

    @Override
    public List<Task> find(Filter filter) {
        return taskDAO.find(filter);
    }

    @Override
    public int countTasks() {
        return taskDAO.count();
    }

    @Override
    public int countTasksByFilter(Filter filter) {
        return taskDAO.countByFilter(filter);
    }

    @Override
    public List<String> getAllTags() {
        List<Task> tasks;
        Map<String, Integer> tags;

        tasks = taskDAO.loadTasks();

        tags = countTags(tasks);
        return new ArrayList<>(tags.keySet());
    }

    @Override
    public Map<String, Integer> getAllTagsWithQuantity() {
        List<Task> tasks;
        Map<String, Integer> tags;

        tasks = taskDAO.loadTasks();

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
    public boolean deleteTask(UUID id) {
        return taskDAO.delete(id);
    }

    @Override
    public boolean deleteByFilter(Filter filter) {
        return taskDAO.deleteByFilter(filter);
    }

    @Override
    public String getInfo(UUID id) {
        List<Task> tasks;
        Task current = null;
        current = taskDAO.read(id);

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

    @Override
    public boolean exportAll() {
        List<Task> tasks;
        boolean result = false;

        tasks = taskDAO.loadTasks();

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
        catch(IOException e){
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