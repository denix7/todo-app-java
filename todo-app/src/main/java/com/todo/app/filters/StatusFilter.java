package com.todo.app.filters;

import com.todo.app.entities.Task;

public class StatusFilter implements Filter {
    private String status;

    public StatusFilter(String status) {
        this.status = status;
    }

    @Override
    public boolean satisfies(Task task) {
        return task.getStatus().equals(this.status);
    }
}