package com.todo.app.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class Task {
    private String description;
    private String status;
    private UUID uuid;
    private String entry;
    private Date start;
    private Date end;
    private Date due;
    private String priority;
    private ArrayList<UUID> depends;
    private String[] tags;

    public Task(String description) {
        this.description = description;
    }
    public Task(String description, String status, UUID uuid, String entry, String priority) {

        this.description = description;
        this.status = status;
        this.uuid = uuid;
        this.entry = entry;
        this.start = start;
        this.end = end;
        this.due = due;
        this.priority = priority;
        this.depends = depends;
        this.tags = tags;
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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
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
        return
                "" + description +
                "," + status +
                "," + uuid +
                "," + entry +
                "," + priority;
                //"," + depends +
                //"," + Arrays.toString(tags);
    }
}