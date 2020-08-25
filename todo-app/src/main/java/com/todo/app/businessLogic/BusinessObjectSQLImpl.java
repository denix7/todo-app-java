package com.todo.app.businessLogic;

import com.todo.app.dao.TaskMySqlDAOImpl;
import com.todo.app.entities.Task;

import java.util.ArrayList;

public class BusinessObjectSQLImpl implements IBusinessObject {
    public static TaskMySqlDAOImpl taskDAO;

    public BusinessObjectSQLImpl() {
        taskDAO = new TaskMySqlDAOImpl();
    }

    @Override
    public void addTask(String[] args, String fileName) {
        Task task = new Task(args[0]);
        System.out.println(args[0]);
        try{
            taskDAO.save(task, "", false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void modifyTask(String[] args, String fileName) {

    }

    @Override
    public void modifyTaskByIndex(ArrayList<Task> tasks, String fileName, String[] args) {

    }

    @Override
    public void doneTask(String[] args, String fileName) {

    }

    @Override
    public void listTasks(String[] args, String fileName) {

    }

    @Override
    public ArrayList<Task> filterByTag(ArrayList<Task> tasks, String tag) {
        return null;
    }

    @Override
    public void loadFile(String fileName) {

    }
}
