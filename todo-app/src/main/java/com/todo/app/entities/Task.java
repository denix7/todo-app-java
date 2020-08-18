package com.todo.app.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Task {
    private String description;
    private String status;
    private UUID uuid;
    private Date entry;
    private Date start;
    private Date end;
    private Date due;
    private String priority;
    private ArrayList<UUID> depends;
    private String[] tags;

    public Task(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEntry() {
        return entry;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public ArrayList<UUID> getDepends() {
        return depends;
    }

    public void setDepends(ArrayList<UUID> depends) {
        this.depends = depends;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Task{" +
                "IIUD=" + uuid +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}