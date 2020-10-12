package com.todo.app.dependencyInjection;

import com.todo.app.aplication.TaskServiceImp;
import com.todo.app.domain.entities.Task;
import com.todo.app.infrastructure.TaskDAO;
import com.todo.app.infrastructure.TaskMySqlDAOImpl;

public class Injector {
    public static TaskServiceImp getTaskService(){
        return new TaskServiceImp();
    }

    public static TaskDAO getTaskDao() {
        return new TaskMySqlDAOImpl();
    }

    public static Task getTask() {
        return new Task();
    }
}