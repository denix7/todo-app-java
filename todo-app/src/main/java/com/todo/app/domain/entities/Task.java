package com.todo.app.domain.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    private int id;
    private String description;
    private String status;
    private UUID uuid;
    private String entry;
    private Date start;
    private Date end;
    private String due;
    private String priority;
    private ArrayList<UUID> depends;
    private String tag;

    public Task(){}

    public Task(String description) {
        this.description = description;
    }

    public Task(String description, String priority) {
        this.description = description;
        this.priority = priority;
    }

    public Task(String description, String status, UUID uuid, String entry, String priority, String tag) {
        this.description = description;
        this.status = status;
        this.uuid = uuid;
        this.entry = entry;
        this.start = start;
        this.end = end;
        this.due = due;
        this.priority = priority;
        this.depends = depends;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return
                "" + description +
                "," + status +
                "," + uuid +
                "," + entry +
                "," + priority +
                "," + tag;
    }

    public String showList() {
        return
                "[description : "+ description + " | "+
                "status: " + status + " | "+
                "id " + uuid + " | "+
                "create : " + entry + " | "+
                "priority : " + priority + " | "+
                "tag : " + tag + "]" + " | " +
                "due : " + due;
    }
}