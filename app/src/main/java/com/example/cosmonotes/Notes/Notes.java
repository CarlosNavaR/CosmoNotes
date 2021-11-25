package com.example.cosmonotes.Notes;

import java.time.LocalDate;
import java.util.Date;

public class Notes {
    private Integer id_notas;
    private String titulo;
    private String contenido;
    private String ColorCategoria;
    private LocalDate FechaNota;
    private Integer encode;
    // Aqui iria id_usuario gg

    // Constructor vacio
    public Notes(){}

    // Constructor
    public Notes(String titulo, String contenido, String ColorCategoria,LocalDate fechaNota,Integer encode) { // Usuario id_usuario
        this.titulo = titulo;
        this.contenido = contenido;
        this.ColorCategoria = ColorCategoria;
        this.encode = encode;
        this.FechaNota = fechaNota;
        // this.id_usuario = id_usuario;
    }

    public LocalDate getFechaNota() {
        return FechaNota;
    }

    public void setFechaNota(String date) {
        this.FechaNota = LocalDate.parse(date);
    }

    public String getColorCategoria() {
        return ColorCategoria;
    }

    public void setColorCategoria(String colorCategoria) {
        ColorCategoria = colorCategoria;
    }

    // Propiedades
    public Integer getId_notas() {
        return id_notas;
    }

    public void setId_notas(Integer id_notas) {
        this.id_notas = id_notas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Integer getEncode() {
        return encode;
    }

    public void setEncode(Integer encode) {
        this.encode = encode;
    }

}
