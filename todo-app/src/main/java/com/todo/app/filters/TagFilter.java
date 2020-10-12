package com.todo.app.filters;

import com.todo.app.domain.entities.Task;

public class TagFilter implements Filter {
    private String tag;
    private static final String name = "tag";

    public TagFilter(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean satisfies(Task task) {
        return task.getTag().equals(this.tag);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.tag;
    }
}