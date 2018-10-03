package com.example.lucasgaleano.micarrera.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "teacher_table")
public class teacher {

    @Ignore
    public static final int TITULAR = 0;
    public static final int AYUDANTE = 1;
    public static final int JTP = 2; //jefe de TP
    public static final int JC = 3; //jefe de catedra

    @PrimaryKey(autoGenerate = true)
    private int id_teacher;
    private String name;
    private String Subject;
    private int Type;
    private String email;
    private String webSite;

    public int getId_teacher() {
        return id_teacher;
    }

    public void setId_teacher(int id_teacher) {
        this.id_teacher = id_teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public static String get(int type){
        switch (type){
            case TITULAR:
                return "Titular";
            case AYUDANTE:
                return "Ayudante";
            case JTP:
                return "Jefe de trabajos practicos";
            case JC:
                return "jefe de catedra";
            default:
                return null;
        }
    }
}
