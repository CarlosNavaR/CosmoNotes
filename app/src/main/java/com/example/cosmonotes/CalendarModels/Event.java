package com.example.cosmonotes.CalendarModels;

import com.example.cosmonotes.Utils.DataBaseHelper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {
    public int Id;
    public String titulo;
    private LocalDate fecha;
    private LocalTime hora;
    private String colorNote;

    public static ArrayList<Event> eventsForDate(LocalDate date, DataBaseHelper db){
        ArrayList<Event> eventos = new ArrayList<>();
        for (Event event : db.getAllEvents()){
            if (event.getDate().equals(date))
                eventos.add(event);
        }
        return eventos;
    }

    public Event(){}
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

    public void setDate(String date) {
        this.fecha = LocalDate.parse(date);
    }

    public LocalTime getTime() {
        return hora;
    }

    public void setTime(String time) {
        this.hora = LocalTime.parse(time);
    }

    public String getColorNote() {
        return colorNote;
    }

    public void setColorNote(String colorNote) {
        this.colorNote = colorNote;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
