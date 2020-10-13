package com.todo.app.dependencyInjection;

import com.todo.app.aplication.TaskServiceImp;
import com.todo.app.domain.entities.Task;
import com.todo.app.infrastructure.TaskDAO;
import com.todo.app.infrastructure.TaskHibernateDAOImpl;
import com.todo.app.infrastructure.TaskMySqlDAOImpl;
import com.todo.app.infrastructure.TaskTxtDAOImpl;

public class Injector {
    public static TaskServiceImp getTaskService(){
        return new TaskServiceImp();
    }

    public static TaskDAO getTaskMySqlDao() {
        return new TaskMySqlDAOImpl();
    }

    public static TaskDAO getTaskMyFileDao() {
        return new TaskTxtDAOImpl();
    }

    public static TaskDAO getTaskMyHibernateDao() {
        return new TaskHibernateDAOImpl();
    }

    public static Task getTask() {
        return new Task();
    }
}