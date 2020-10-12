package com.todo.app.filters;

import com.todo.app.domain.entities.Task;

public class StatusFilter implements Filter {
    private String status;
    private static final String name = "status";

    public StatusFilter(String status) {
        this.status = status;
    }

    @Override
    public boolean satisfies(Task task) {
        return task.getStatus().equals(this.status);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.status;
    }
}