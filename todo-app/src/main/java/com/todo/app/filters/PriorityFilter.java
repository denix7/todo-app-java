package com.todo.app.filters;

import com.todo.app.domain.entities.Task;

public class PriorityFilter implements Filter {
    private String priority;
    private String name = "priority";

    public PriorityFilter(String priority) {
        this.priority = priority;
    }

    @Override
    public boolean satisfies(Task task) {
        return task.getPriority().equals(this.priority);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.priority;
    }
}