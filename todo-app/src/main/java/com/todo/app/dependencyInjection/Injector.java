package com.todo.app.dependencyInjection;

import com.todo.app.aplication.BusinessObjectSQLImpl;
import com.todo.app.domain.entities.Task;
import com.todo.app.infrastructure.TaskDAO;
import com.todo.app.infrastructure.TaskMySqlDAOImpl;

public class Injector {
    public static BusinessObjectSQLImpl getBusinessObject(){
        return new BusinessObjectSQLImpl();
    }

    public static TaskDAO getTaskDao() {
        return new TaskMySqlDAOImpl();
    }

    public static Task getTask() {
        return new Task();
    }
}