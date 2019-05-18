package com.example.pyrca.micarrera.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Calendar;

@Entity(tableName = "assignment_table")
public class Assignment{

    @Ignore
    public static final int PENDIENTE = 0;
    @Ignore
    public static final int TERMINADA = 1;

    @PrimaryKey(autoGenerate= true)
    private int id;
    @NonNull
    private Calendar date;
    private String subject;
    private String description;
    @NonNull
    private String title;
    private String photo;
    private String Audio;
    private int state;

    @Ignore
    public Assignment (String subject){
        this(Calendar.getInstance(), subject, "Cosas\nMuchas cosas\nMuchas otras cosas mas", "nueva tarea", "", "", 0);

    }

    public Assignment(Calendar date,String subject,String description,String title, String photo, String Audio, int state ) {
        this.date = date;
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.photo=photo;
        this.Audio=Audio;
        this.state=state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id_assignment) {
        this.id = id_assignment;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Ignore
    public void setDate(int year, int month, int day) {
        this.date.set(year,month,day);
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
        return this.title + "  " + this.getday() + '/' + this.getmonth() + '/' + this.getyear();
    }

    @Ignore
    public int getyear(){ return this.getDate().get(Calendar.YEAR);}

    @Ignore
    public int getmonth(){ return this.getDate().get(Calendar.MONTH);}

    @Ignore
    public int getday(){ return this.getDate().get(Calendar.DAY_OF_MONTH);}
}
