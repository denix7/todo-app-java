package com.todo.app.filters;

import com.todo.app.domain.entities.Task;

public class TagFilter implements Filter {
    private String tag;

    public TagFilter(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean satisfies(Task task) {
        return task.getTag().equals(this.tag);
    }
}