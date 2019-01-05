package com.example.lucasgaleano.micarrera.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.lucasgaleano.micarrera.classes.Calendario;

import java.util.Calendar;

@Entity(tableName = "exam_table")
public class Exam {

    @Ignore
    public static final int PARCIAL1 = 0;
    @Ignore
    public static final int PARCIAL2 = 1;
    @Ignore
    public static final int PARCIAL3 = 2;
    @Ignore
    public static final int RECUPERATORIO11 = 3;
    @Ignore
    public static final int RECUPERATORIO12 = 4;
    @Ignore
    public static final int RECUPERATORIO13 = 5;
    @Ignore
    public static final int RECUPERATORIO21 = 6;
    @Ignore
    public static final int RECUPERATORIO22 = 7;
    @Ignore
    public static final int RECUPERATORIO23 = 8;
    @Ignore
    public static final int FINAL = 9;



    @PrimaryKey(autoGenerate = true)
    private int id_exam;
    private String subject;
    private Calendar date;
    private int type;
    private float score;
    private String description;

    public Exam(String subject, Calendar date, int type, float score,String description ) {

        this.setSubject(subject);
        this.setDate(date);
        this.setType(type);
        this.setScore(score);
        this.setDescription(description);
    }

    public int getId_exam() {
        return id_exam;
    }

    public void setId_exam(int id_exam) {
        this.id_exam = id_exam;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String get(int type) {
        switch (type) {
            case PARCIAL1:
                return "1 Parcial";
            case PARCIAL2:
                return "2 Parcial";
            case PARCIAL3:
                return "3 Parcial";
            
            case RECUPERATORIO11:
                return "1°1° Recuperatorio";
            
            case RECUPERATORIO12:
                return "2°1° Recuperatorio";
            
            case RECUPERATORIO13:
                return "3°1° Recuperatorio";
            
            case RECUPERATORIO21:
                return "1°2° Recuperatorio";
            
            case RECUPERATORIO22:
                return "2°2° Recuperatorio";
            
            case RECUPERATORIO23:
                return "3°2° Recuperatorio";
            
            case FINAL:
                return "Final";
            
            default:
                return null;
        }
    }



    @Override
    public String toString() {
        return this.get(this.type) +" "+ Calendario.formatDate(this.getDate());
    }

}
