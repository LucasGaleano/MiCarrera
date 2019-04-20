package com.example.lucasgaleano.micarrera.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "teacher_table")
public class Teacher {

    @Ignore
    public static final int TITULAR = 0;
    @Ignore
    public static final int AYUDANTE = 1;
    @Ignore
    public static final int JTP = 2; //jefe de TP
    @Ignore
    public static final int JC = 3; //jefe de catedra

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String subject;
    private int type;
    private String email;
    private String webSite;

    @Ignore
    public Teacher(String subject){
        this("Profesor Nuevo" ,subject, 0, "miProfesor@gmail.com", "miProfesor.com");
    }


    public Teacher(String name, String subject, int type, String email, String webSite){
        this.setName(name);
        this.setSubject(subject);
        this.setType(type);
        this.setEmail(email);
        this.setWebSite(webSite);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    @Override
    public String toString() {
        return this.name;
    }
}
