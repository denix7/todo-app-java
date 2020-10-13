package com.todo.app.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class TaskH {


//    private UUID uuid;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private Status status;
//    private String entry;
//    private Date start;
//    private Date end;
//    private String due;
//    private Priority priority;
    //private ArrayList<UUID> depends;
//    private ArrayList <String> tags;

//    public UUID getUuid() {
//        return uuid;
//    }
//
//    public void setUuid(UUID uuid) {
//        this.uuid = uuid;
//    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

//    public String getEntry() {
//        return entry;
//    }
//
//    public void setEntry(String entry) {
//        this.entry = entry;
//    }
//
//    public Date getStart() {
//        return start;
//    }
//
//    public void setStart(Date start) {
//        this.start = start;
//    }
//
//    public Date getEnd() {
//        return end;
//    }
//
//    public void setEnd(Date end) {
//        this.end = end;
//    }
//
//    public String getDue() {
//        return due;
//    }
//
//    public void setDue(String due) {
//        this.due = due;
//    }
//
//    public Priority getPriority() {
//        return priority;
//    }
//
//    public void setPriority(Priority priority) {
//        this.priority = priority;
//    }

//    public ArrayList<UUID> getDepends() {
//        return depends;
//    }

//    public void setDepends(ArrayList<UUID> depends) {
//        this.depends = depends;
//    }

//    public ArrayList<String> getTags() {
//        return tags;
//    }
//
//    public void setTags(ArrayList<String> tags) {
//        this.tags = tags;
//    }
}
