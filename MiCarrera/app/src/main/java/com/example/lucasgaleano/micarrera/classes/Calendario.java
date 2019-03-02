package com.example.lucasgaleano.micarrera.classes;

import android.util.Log;

import com.example.lucasgaleano.micarrera.database.Exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Calendario{

    public static boolean iguales(Calendar date1, Calendar date2) {

        return date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH)
                    && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)
                    && date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR);
    }

    public static List<Exam> filtrarExamenesPorFecha(List<Exam> listaExamenes, Calendar aux) {

        List<Exam> ExamenesPorFecha = new ArrayList<>();
        for (Exam examen : listaExamenes){
            if(Calendario.iguales(examen.getDate(),aux))
                ExamenesPorFecha.add(examen);
        }
        return ExamenesPorFecha;
    }

    public static List<Exam> filtrarExamenesPorFecha(List<Exam> listaExamenes, int year, int month, int day) {

        Calendar aux = Calendar.getInstance();
        aux.set(year,month,day);
        List<Exam> ExamenesPorFecha = new ArrayList<>();
        for (Exam examen : listaExamenes){
            if(Calendario.iguales(examen.getDate(),aux))
                ExamenesPorFecha.add(examen);
        }
        return ExamenesPorFecha;
    }

    public static String formatDate(Calendar cal) {

        return String.valueOf(cal.get(Calendar.DAY_OF_MONTH)).concat(
                "/"+ String.valueOf(cal.get(Calendar.MONTH)).concat(
                        "/"+ String.valueOf(cal.get(Calendar.YEAR))));

    }

}
