package com.example.cosmonotes.todoModels;

public class toDoModel {
    private int idItem;
    private String task;
    private int status, group;

    public toDoModel(){}

    public toDoModel(String task, int status, int group) {
        this.task = task;
        this.status = status;
        this.group = group;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
