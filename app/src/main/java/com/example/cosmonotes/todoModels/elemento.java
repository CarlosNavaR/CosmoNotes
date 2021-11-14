package com.example.cosmonotes.todoModels;

public class elemento {
    private String Titulo;
    private boolean Estado;
    private int Orden;
    private int Group;
// Constructores


    // Este es para nuevos elementos
    public elemento(String Titulo, int Group) {
        this.Titulo = Titulo;
        this.Estado = false;
        this.Group = Group;
        this.Orden = 0;
    }
    // Este es para constuctor por guardados
    public elemento(String Titulo, boolean Estado, int Group) {
        this.Titulo = Titulo;
        this.Estado = Estado;
        this.Group = Group;
        this.Orden = 0;
    }

    public elemento(String Titulo, boolean Estado, int Group, int Orden) {
        this.Titulo = Titulo;
        this.Estado = Estado;
        this.Group = Group;
        this.Orden = Orden;
    }

    public elemento() {
        this.Titulo = "Vacio...";
        this.Estado = false;
        this.Group = 0;
        this.Orden = 0;
    }

    // GETTER SETTER
    public String getTitulo() {
        return Titulo;
    }
    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }
    public boolean isEstado() {
        return Estado;
    }
    public void setEstado(boolean Estado) {
        this.Estado = Estado;
    }
    public int getGroup() {
        return Group;
    }
    public void setGroup(int Group) {
        this.Group = Group;
    }
    public int getOrden() {
        return Orden;
    }
    public void setOrden(int Orden) {
        this.Orden = Orden;
    }
}