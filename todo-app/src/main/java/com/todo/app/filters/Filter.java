package com.todo.app.filters;

import com.todo.app.entities.Task;

public interface Filter {
    public boolean satisfies(Task task);
}
