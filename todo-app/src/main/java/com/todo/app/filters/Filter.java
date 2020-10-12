package com.todo.app.filters;

import com.todo.app.domain.entities.Task;

public interface Filter {
    public boolean satisfies(Task task);
    public String getName();
    public String getValue();
}
