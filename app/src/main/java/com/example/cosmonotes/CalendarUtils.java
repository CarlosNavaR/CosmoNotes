package com.example.cosmonotes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarUtils {

    public static LocalDate selectedDate;

    public static String formatoFecha(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return  date.format(formatter);
    }

    public static String formatoTIempo(LocalTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return time.format(formatter);
    }

    public static String mesAnioFecha(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static ArrayList<LocalDate> diasMesArray(LocalDate date){
        ArrayList<LocalDate> diasMesArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int diasMes = yearMonth.lengthOfMonth();

        LocalDate primerDiaMes = CalendarUtils.selectedDate.withDayOfMonth(1);
        int diaSemana = primerDiaMes.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++){
            if(i <= diaSemana || i > diasMes + diaSemana)
                diasMesArray.add(null);
            else
                diasMesArray.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), i - diaSemana));
        }
        return diasMesArray;
    }

    public static ArrayList<LocalDate> diasSemanaArray(LocalDate selectedDate){
        ArrayList<LocalDate> dias = new ArrayList<>();
        LocalDate current = Inicio(selectedDate);
        LocalDate endDate = current.plusWeeks(1);

        while (current.isBefore(endDate)){
            dias.add(current);
            current = current.plusDays(1);
        }
        return dias;
    }

    public static LocalDate Inicio(LocalDate current){
        LocalDate semanaPasada = current.minusWeeks(1);

        while (current.isAfter(semanaPasada)){
            if (current.getDayOfWeek()== DayOfWeek.SUNDAY)
                return current;
            current = current.minusDays(1);
        }
        return null;
    }

}
