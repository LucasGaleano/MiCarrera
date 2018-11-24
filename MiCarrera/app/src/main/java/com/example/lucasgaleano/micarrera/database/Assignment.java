package com.example.lucasgaleano.micarrera.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "assignment_table")
public class Assignment {

    @Ignore
    public static final int PENDIENTE = 0;
    @Ignore
    public static final int TERMINADA = 1;

    @PrimaryKey(autoGenerate= true)
    private int id_assignment;
    private Calendar date;
    private String subject;
    private String description;
    private String title;
    private String photo;
    private String Audio;
    private int state;


    public int getId_assignment() {
        return id_assignment;
    }

    public void setId_assignment(int id_assignment) {
        this.id_assignment = id_assignment;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAudio() {
        return Audio;
    }

    public void setAudio(String audio) {
        Audio = audio;
    }

    public static String get(int type) {
        switch (type) {
            case PENDIENTE:
                return "Pendiente";
            case TERMINADA:
                return "Terminada";
            default:
                return null;
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
