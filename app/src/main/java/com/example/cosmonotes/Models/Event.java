package com.example.cosmonotes.Models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {
    public String titulo;
    private LocalDate fecha;
    private LocalTime hora;
    private String colorNote;

    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date){
        ArrayList<Event> eventos = new ArrayList<>();

        for (Event event : eventsList){
            if (event.getDate().equals(date))
                eventos.add(event);

        }
        return eventos;
    }

    public Event(String titulo, LocalDate fecha, LocalTime hora, String selectedCOlor){
        this.titulo = titulo;
        this.fecha = fecha;
        this.hora = hora;
        this.colorNote = selectedCOlor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getDate() {
        return fecha;
    }

    public void setDate(LocalDate date) {
        this.fecha = date;
    }

    public LocalTime getTime() {
        return hora;
    }

    public void setTime(LocalTime time) {
        this.hora = time;
    }

    public String getColorNote() {
        return colorNote;
    }

    public void setColorNote(String colorNote) {
        this.colorNote = colorNote;
    }


}
