package com.example.cosmonotes.todoModels;

public class groupModel {
    private int IdGroup;
    private String titleGroup;
    private String colorGroup;
    private int idUser;
    private boolean status;

    public groupModel(){}

    public groupModel(String titleGroup, boolean status, int idUser) {
        this.titleGroup = titleGroup;
        this.status = status;
        this.idUser = idUser;
    }

    public int getIdGroup() {
        return IdGroup;
    }

    public void setIdGroup(int idGroup) {
        IdGroup = idGroup;
    }

    public String getTitleGroup() {
        return titleGroup;
    }

    public void setTitleGroup(String task) {
        this.titleGroup = task;
    }

    public String getColorGroup() {
        return colorGroup;
    }

    public void setColorGroup(String colorGroup) {
        this.colorGroup = colorGroup;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
