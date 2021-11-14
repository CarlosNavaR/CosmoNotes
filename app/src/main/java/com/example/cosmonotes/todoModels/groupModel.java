package com.example.cosmonotes.todoModels;

public class groupModel {
    private String titleGroup;
    private int status, idUser;

    public String getTitleGroup() {
        return titleGroup;
    }

    public void setTitleGroup(String task) {
        this.titleGroup = task;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
